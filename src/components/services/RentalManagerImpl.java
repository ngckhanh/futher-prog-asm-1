package services;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;
import models.enums.AgreementStatus;
import models.interfaces.RentalManager;
import models.managers.Properties;
import utils.CommercialPropertyFileUtils.CommercialPropertyWriteFile;
import utils.HostFileUtils.HostReadFile;
import utils.HostFileUtils.HostWriteFile;
import utils.OwnerFileUtils.OwnerReadFile;
import utils.OwnerFileUtils.OwnerWriteFile;
import utils.RentalAgreementFileUtils.RentalAgreementReadFile;
import utils.RentalAgreementFileUtils.RentalAgreementWriteFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyWriteFile;
import utils.TenantFileUtils.TenantReadFile;
import utils.TenantFileUtils.TenantWriteFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.HostFileUtils.HostWriteFile.updateHostFile;
import static utils.TenantFileUtils.TenantWriteFile.updateTenantFile;

public class RentalManagerImpl implements RentalManager {
    private List<RentalAgreement> rentalAgreements;
    private List<Host> hosts;
    private List<Tenant> tenants;
    List<CommercialProperty> commercialProperties;
    List<ResidentialProperty> residentialProperties;

    public RentalManagerImpl() {
        this.rentalAgreements = new ArrayList<>();
        this.hosts = new ArrayList<>();
        this.tenants = new ArrayList<>();
        this.commercialProperties = new ArrayList<>();
        this.residentialProperties = new ArrayList<>();
        loadRentalAgreementsFromFile();
    }

    private void loadRentalAgreementsFromFile() {
        try {
            List<RentalAgreement> agreementsFromFile = RentalAgreementReadFile.readRentalAgreementsFromFile();
            rentalAgreements.addAll(agreementsFromFile);
        } catch (IOException e) {
            System.out.println("Error loading rental agreements from file: " + e.getMessage());
        }
    }

    @Override
    public void addRentalAgreement(RentalAgreement agreement) throws IOException {
        if (agreement != null) {
            // Add the agreement to the rental agreements list
            rentalAgreements.add(agreement);

            // Update the host and property
            updateHostAndProperty(agreement);

            // Update the owner with the host and property
            updateOwnerWithHostAndProperty(agreement);

            // Read existing hosts and tenants from their files
            List<Host> hosts = HostReadFile.readHostsFromFile();
            List<Tenant> tenants = TenantReadFile.readTenantsFromFile();

            // Update the host's rental agreements
            updateHostRentalAgreements(agreement, hosts);

            // Update the main tenant's rental agreements
            updateTenantRentalAgreements(agreement, tenants);

            // Write the updated hosts and tenants back to their files
            writeUpdatedDataToFile(hosts, tenants);
        } else {
            System.out.println("The provided rental agreement is null.");
        }
    }

    // Method to update the host's rental agreements
    private void updateHostRentalAgreements(RentalAgreement agreement, List<Host> hosts) {
        if (agreement.getHost() != null) {
            Host host = agreement.getHost();
            host.addRentalAgreement(agreement); // Update the host's rental agreements

            boolean hostFound = false;
            for (int i = 0; i < hosts.size(); i++) {
                if (hosts.get(i).getId().equals(host.getId())) {
                    hosts.set(i, host); // Update the existing host
                    hostFound = true;
                    break;
                }
            }
            if (!hostFound) {
                hosts.add(host); // Add new host if not found
            }
        }
    }

    // Method to update the main tenant's rental agreements
    private void updateTenantRentalAgreements(RentalAgreement agreement, List<Tenant> tenants) {
        if (agreement.getMainTenant() != null) {
            Tenant mainTenant = agreement.getMainTenant();
            mainTenant.addRentalAgreement(agreement); // Update the main tenant's rental agreements
            mainTenant.addRentedProperty(agreement.getProperty()); // Add property to the main tenant's rented property list

            boolean tenantFound = false;
            for (int i = 0; i < tenants.size(); i++) {
                if (tenants.get(i).getId().equals(mainTenant.getId())) {
                    tenants.set(i, mainTenant); // Update the existing tenant
                    tenantFound = true;
                    break;
                }
            }
            if (!tenantFound) {
                tenants.add(mainTenant); // Add new tenant if not found
            }
        }

        // Update each sub-tenant's rental agreements
        for (Tenant subTenant : agreement.getSubTenants()) {
            if (subTenant != null) {
                subTenant.addRentalAgreement(agreement); // Update the sub-tenant's rental agreements
                subTenant.addRentedProperty(agreement.getProperty()); // Add property to the sub-tenant's rented property list

                boolean subTenantFound = false;
                for (int i = 0; i < tenants.size(); i++) {
                    if (tenants.get(i).getId().equals(subTenant.getId())) {
                        tenants.set(i, subTenant); // Update the existing sub-tenant
                        subTenantFound = true;
                        break;
                    }
                }
                if (!subTenantFound) {
                    tenants.add(subTenant); // Add new sub-tenant if not found
                }
            }
        }
    }

    // Method to write updated hosts and tenants back to their files
    private void writeUpdatedDataToFile(List<Host> hosts, List<Tenant> tenants) {
        try {
            HostWriteFile.writeHostToFile(hosts, "src/components/resource/data/hostData/host.txt");
            TenantWriteFile.writeTenantToFile(tenants);
            RentalAgreementWriteFile.writeRentalAgreementToFile(rentalAgreements, "src/components/resource/data/rentalAgreementData/rental_agreement.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void updateHostAndProperty(RentalAgreement agreement) {
        if (agreement == null) {
            System.out.println("The provided rental agreement is null.");
            return;
        }

        // Get the host and property from the agreement
        Host host = agreement.getHost();
        Property property = agreement.getProperty();

        // Read existing hosts from file
        List<Host> hosts;
        try {
            hosts = HostReadFile.readHostsFromFile(); // Read the list of hosts
        } catch (IOException e) {
            System.out.println("Error reading hosts from file: " + e.getMessage());
            return; // Exit the method if there's an error
        }

        // Initialize properties manager and get residential and commercial properties
        Properties propertiesManager = new Properties();
        List<ResidentialProperty> residentialProperties = propertiesManager.getResidentialProperties();
        List<CommercialProperty> commercialProperties = propertiesManager.getCommercialProperties();

        // Ensure property is found
        if (property != null) {
            // Add the property to the host's managed property list
            host.addManagedProperty(property);

            // Add the host to the property's host list
            String hostId = host.getId(); // Get the host ID from the agreement
            String propertyId = property.getPropertyId(); // Get the property ID from the agreement

            // Find the property by ID
            Property newProperty = propertiesManager.findPropertyById(propertyId, residentialProperties, commercialProperties);
            if (newProperty != null) {
                // Update the host list in the property
                if (newProperty instanceof CommercialProperty) {
                    CommercialProperty commercialProperty = (CommercialProperty) newProperty;
                    if (!commercialProperty.getHostList().contains(host)) {
                        commercialProperty.getHostList().add(host); // Add the host to the property's host list
                        //System.out.println("Added host " + hostId + " to commercial property " + propertyId);
                    } else {
                        //System.out.println("Host " + hostId + " already exists in commercial property " + propertyId);
                    }
                } else if (newProperty instanceof ResidentialProperty) {
                    ResidentialProperty residentialProperty = (ResidentialProperty) newProperty;
                    if (!residentialProperty.getHostList().contains(host)) {
                        residentialProperty.getHostList().add(host); // Add the host to the property's host list
                        //System.out.println("Added host " + hostId + " to residential property " + propertyId);
                    } else {
                        //System.out.println("Host " + hostId + " already exists in residential property " + propertyId);
                    }
                }
            } else {
                System.out.println("Property not found for ID: " + propertyId);
            }
        } else {
            System.out.println("Property not found for the rental agreement.");
            return; // Exit if property is not found
        }

        // Write the updated hosts back to their files
        try {
            HostWriteFile.writeHostToFile(hosts, "src/components/resource/data/hostData/host.txt");

            // Save updated properties back to their respective files
            ResidentialPropertyWriteFile.writeResidentialPropertiesToFile("src/components/resource/data/propertyData/residential_property.txt", residentialProperties);
            CommercialPropertyWriteFile.writeCommercialPropertiesToFile("src/components/resource/data/propertyData/commercial_property.txt", commercialProperties);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void updateOwnerWithHostAndProperty(RentalAgreement agreement) throws IOException {
        if (agreement == null) {
            System.out.println("The provided rental agreement is null.");
            return;
        }

        // Get the host and property from the agreement
        Host host = agreement.getHost();
        Property property = agreement.getProperty();

        // Ensure host and property are not null
        if (host != null && property != null) {
            // Retrieve the owner associated with the property
            Owner owner = OwnerReadFile.getOwnerByProperty(property); // Assuming a method to retrieve the owner by property

            if (owner != null) {
                // Add the host to the owner's host list
                if (!owner.getHostList().contains(host)) {
                    owner.getHostList().add(host); // Add the host object to the owner's host list
                    //System.out.println("Added host " + host.getId() + " to owner " + owner.getFullName());
                } else {
                    //System.out.println("Host " + host.getId() + " already exists in owner's host list.");
                }

                // Add the property to the owner's owned property list
                if (!owner.getOwnedPropertyList().contains(property)) {
                    owner.getOwnedPropertyList().add(property); // Add the property object to the owner's owned property list
                    //System.out.println("Added property " + property.getPropertyId() + " to owner " + owner.getFullName());
                } else {
                    //System.out.println("Property " + property.getPropertyId() + " already exists in owner's owned property list.");
                }

                // Read all owners from the file
                List<Owner> ownerList = OwnerReadFile.readOwnersFromFile();

                // Update the list with the modified owner
                for (int i = 0; i < ownerList.size(); i++) {
                    if (ownerList.get(i).getId().equals(owner.getId())) {
                        ownerList.set(i, owner); // Replace the existing owner with the modified one
                        break;
                    }
                }

                // Write the updated list back to the file
                try {
                    OwnerWriteFile.writeOwnerToFile(ownerList, "src/components/resource/data/ownerData/owner.txt");
                } catch (IOException e) {
                    System.out.println("Error writing to owner file: " + e.getMessage());
                }
            } else {
                System.out.println("Owner not found for the property " + property.getPropertyId());
            }
        } else {
            System.out.println("Host or property not found in the rental agreement.");
        }
    }

    @Override
    public void updateRentalAgreement(RentalAgreement currentAgreement, RentalAgreement updatedAgreement) throws IOException {
        if (currentAgreement == null || updatedAgreement == null) {
            System.out.println("One of the provided rental agreements is null.");
            return;
        }

        // Remove old associations
        removeOldAssociations(currentAgreement);

        // Update the current agreement with new values
        currentAgreement.setHost(updatedAgreement.getHost());
        currentAgreement.setProperty(updatedAgreement.getProperty());
        currentAgreement.setMainTenant(updatedAgreement.getMainTenant());
        currentAgreement.setSubTenants(updatedAgreement.getSubTenants());
        currentAgreement.setPeriod(updatedAgreement.getPeriod());
        currentAgreement.setRentalFee(updatedAgreement.getRentalFee());
        currentAgreement.setStatus(updatedAgreement.getStatus());

        // Re-establish associations
        updateOwnerWithHostAndProperty(currentAgreement);
        updateHostAndProperty(currentAgreement);

        // Read existing hosts and tenants from their files
        List<Host> hosts = HostReadFile.readHostsFromFile();
        List<Tenant> tenants = TenantReadFile.readTenantsFromFile();

        // Update the host's rental agreements
        updateHostRentalAgreements(currentAgreement, hosts);

        // Update the main tenant's rental agreements
        updateTenantRentalAgreements(currentAgreement, tenants);

        // Write the updated hosts and tenants back to their files
        writeUpdatedDataToFile(hosts, tenants);
    }

    private void removeOldAssociations(RentalAgreement agreement) throws IOException {
        // Remove the old host's rental agreement
        Host oldHost = agreement.getHost();
        if (oldHost != null) {
            //System.out.println("Removing rental agreement from old host: " + oldHost.getId());
            oldHost.removeRentalAgreement(agreement.getAgreementId());
            oldHost.removeManagedProperty(agreement.getProperty());
        } else {
            //System.out.println("No host associated with this agreement.");
        }

        // Remove the old main tenant's rental agreement
        Tenant oldMainTenant = agreement.getMainTenant();
        if (oldMainTenant != null) {
            //System.out.println("Removing rental agreement from old main tenant: " + oldMainTenant.getId());
            oldMainTenant.removeRentalAgreement(agreement.getAgreementId());
            oldMainTenant.removeRentedProperty(agreement.getProperty());
        } else {
            //System.out.println("No main tenant associated with this agreement.");
        }

        // Remove the old sub-tenants' rental agreements
        for (Tenant subTenant : agreement.getSubTenants()) {
            if (subTenant != null) {
                //System.out.println("Removing rental agreement from sub-tenant: " + subTenant.getId());
                subTenant.removeRentalAgreement(agreement.getAgreementId());
                subTenant.removeRentedProperty(agreement.getProperty());
            } else {
                //System.out.println("Found a null sub-tenant, skipping.");
            }
        }


        // Collect updated entities for writing
        List<Host> updatedHosts = new ArrayList<>();
        List<Tenant> updatedTenants = new ArrayList<>();

        if (oldHost != null) {
            updatedHosts.add(oldHost);
        }
        if (oldMainTenant != null) {
            updatedTenants.add(oldMainTenant);
        }
        for (Tenant subTenant : agreement.getSubTenants()) {
            if (subTenant != null) {
                updatedTenants.add(subTenant);
            }
        }

        // Write updated data to files
        writeUpdatedDataLineInFile(updatedHosts, updatedTenants);
    }

    // Method to write updated data lines for hosts and tenants
    public static void writeUpdatedDataLineInFile(List<Host> updatedHosts, List<Tenant> updatedTenants) throws IOException {
        // File paths for hosts and tenants
        String hostFilePath = "src/components/resource/data/hostData/host.txt";
        String tenantFilePath = "src/components/resource/data/tenantData/tenant.txt";

        // Update hosts
        updateHostFile(updatedHosts, hostFilePath);

        // Update tenants
        updateTenantFile(updatedTenants, tenantFilePath);
    }

    @Override
    public void deleteRentalAgreement(String agreementId) throws IOException {
        // Find the rental agreement by ID
        RentalAgreement agreementToDelete = rentalAgreements.stream()
                .filter(agreement -> agreement.getAgreementId().equals(agreementId))
                .findFirst()
                .orElse(null);

        if (agreementToDelete == null) {
            System.out.println("Rental agreement with ID " + agreementId + " not found.");
            return;
        }

        // Remove old associations
        removeOldAssociations(agreementToDelete);

        // Call deleteHostAndProperty to handle host and property removal
        deleteHostAndProperty(agreementToDelete);


        // Remove the agreement from the list of agreements
        rentalAgreements.removeIf(existingAgreement -> existingAgreement.getAgreementId().equals(agreementId));

        // Write the updated hosts back to their files
        List<Host> updatedHosts = HostReadFile.readHostsFromFile(); // Read current host list
        for (int i = 0; i < updatedHosts.size(); i++) {
            Host host = updatedHosts.get(i);
            if (host.getId().equals(agreementToDelete.getHost().getId())) {
                host.removeRentalAgreement(agreementToDelete.getAgreementId()); // Remove the specific agreement
                host.removeManagedProperty(agreementToDelete.getProperty()); // Remove the specific property

                updatedHosts.set(i, host); // Update host without replacing it
            }
        }
        HostWriteFile.writeHostToFile(updatedHosts, "src/components/resource/data/hostData/host.txt"); // Write updated hosts to file

        // Read tenants once and update both main and sub-tenants
        List<Tenant> updatedTenants = TenantReadFile.readTenantsFromFile(); // Read current tenant list
        for (int i = 0; i < updatedTenants.size(); i++) {
            Tenant tenant = updatedTenants.get(i);
            if (tenant.getId().equals(agreementToDelete.getMainTenant().getId())) {
                // Update main tenant without replacing it
                tenant.removeRentalAgreement(agreementToDelete.getAgreementId()); // Remove the specific agreement
                tenant.removeRentedProperty(agreementToDelete.getProperty()); // Remove the specific property
                updatedTenants.set(i, tenant); // Update the tenant in the list
            }

            // Check for sub-tenants
            for (Tenant subTenant : agreementToDelete.getSubTenants()) {
                if (tenant.getId().equals(subTenant.getId())) {
                    // Remove the specific agreement and property for the sub-tenant
                    tenant.removeRentalAgreement(agreementToDelete.getAgreementId()); // Remove the specific agreement
                    tenant.removeRentedProperty(agreementToDelete.getProperty()); // Remove the specific property
                    updatedTenants.set(i, tenant); // Update the tenant in the list
                }
            }
        }
        TenantWriteFile.writeTenantToFile(updatedTenants); // Write updated tenants to file
    }

    public void deleteHostAndProperty(RentalAgreement agreement) {
        if (agreement == null) {
            System.out.println("The provided rental agreement is null.");
            return;
        }

        // Get the host and property from the agreement
        Host host = agreement.getHost();
        Property property = agreement.getProperty();

        // Read existing hosts from file
        List<Host> hosts;
        try {
            hosts = HostReadFile.readHostsFromFile(); // Read the list of hosts
        } catch (IOException e) {
            System.out.println("Error reading hosts from file: " + e.getMessage());
            return; // Exit the method if there's an error
        }

        // Initialize properties manager and get residential and commercial properties
        Properties propertiesManager = new Properties();
        List<ResidentialProperty> residentialProperties = propertiesManager.getResidentialProperties();
        List<CommercialProperty> commercialProperties = propertiesManager.getCommercialProperties();

        // Ensure property is found
        if (property != null) {
            // Remove the property from the host's managed property list
            host.removeManagedProperty(property);

            // Remove the host from the property's host list
            String hostId = host.getId(); // Get the host ID from the agreement
            String propertyId = property.getPropertyId(); // Get the property ID from the agreement

            // Find the property by ID
            Property existingProperty = null;
            if (property instanceof CommercialProperty) {
                existingProperty = commercialProperties.stream()
                        .filter(prop -> prop.getPropertyId().equals(propertyId))
                        .findFirst()
                        .orElse(null);
            } else if (property instanceof ResidentialProperty) {
                existingProperty = residentialProperties.stream()
                        .filter(prop -> prop.getPropertyId().equals(propertyId))
                        .findFirst()
                        .orElse(null);
            }

            if (existingProperty != null) {
                if (existingProperty instanceof CommercialProperty) {
                    CommercialProperty commercialProperty = (CommercialProperty) existingProperty;
                    if (commercialProperty.getHostList().contains(host)) {
                        commercialProperty.getHostList().remove(host); // Remove the host from the property's host list
                        //System.out.println("Removed host " + hostId + " from commercial property " + propertyId);
                    } else {
                        //System.out.println("Host " + hostId + " does not exist in commercial property " + propertyId);
                    }
                } else if (existingProperty instanceof ResidentialProperty) {
                    ResidentialProperty residentialProperty = (ResidentialProperty) existingProperty;
                    if (residentialProperty.getHostList().contains(host)) {
                        residentialProperty.getHostList().remove(host); // Remove the host from the property's host list
                        //System.out.println("Removed host " + hostId + " from residential property " + propertyId);
                    } else {
                        //System.out.println("Host " + hostId + " does not exist in residential property " + propertyId);
                    }
                }
            } else {
                System.out.println("Property not found for ID: " + propertyId);
            }
        } else {
            System.out.println("Property not found for the rental agreement.");
            return; // Exit if property is not found
        }

        // Write the updated hosts back to their files
        try {
            HostWriteFile.writeHostToFile(hosts, "src/components/resource/data/hostData/host.txt");

            // Save updated properties back to their respective files
            ResidentialPropertyWriteFile.writeResidentialPropertiesToFile("src/components/resource/data/propertyData/residential_property.txt", residentialProperties);
            CommercialPropertyWriteFile.writeCommercialPropertiesToFile("src/components/resource/data/propertyData/commercial_property.txt", commercialProperties);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    @Override
    public RentalAgreement getRentalAgreementById(String agreementId) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getAgreementId().equals(agreementId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<RentalAgreement> getAllRentalAgreements() {
        //System.out.println("Rental Agreements Count: " + rentalAgreements.size()); // Debugging
        return new ArrayList<>(rentalAgreements);

    }

    @Override
    public List<RentalAgreement> getNewRentalAgreements() {
        List<RentalAgreement> newRentalAgreements = new ArrayList<>();
        for (RentalAgreement agreement : rentalAgreements) {
            if (agreement.getStatus() == AgreementStatus.NEW) {
                newRentalAgreements.add(agreement);
            }
        }
        return newRentalAgreements;
    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByOwnerName(String ownerName) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getProperty().getOwner().getFullName().equalsIgnoreCase(ownerName))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByPropertyAddress(String propertyAddress) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getProperty().getAddress().equalsIgnoreCase(propertyAddress))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalAgreement> getRentalAgreementsByStatus(String status) {
        return rentalAgreements.stream()
                .filter(agreement -> agreement.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}