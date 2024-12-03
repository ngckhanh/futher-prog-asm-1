package models.entities;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import utils.CommercialPropertyFileUtils.CommercialPropertyReadFile;
import utils.HostFileUtils.HostReadFile;
import utils.OwnerFileUtils.OwnerReadFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyReadFile;
import utils.TenantFileUtils.TenantReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persons {
    private ArrayList<Person> personsList;

    public Persons() {
        System.out.println("Persons initialized" + Thread.currentThread().getStackTrace()[2]);
        this.personsList = new ArrayList<>();
        loadFromFile();
        displayAllPersons();
        //System.out.println(personsList);
    }

    // Method to add a person
    public void addPerson(Person person) {
        personsList.add(person);
    }

    // Method to save persons to disk
    public void saveToDisk(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(personsList);
        } catch (IOException e) {
            System.err.println("Error saving persons to disk: " + e.getMessage());
        }
    }

    // Method to load persons from file
    public void loadFromFile() {
        try {
            List<Host> hosts = HostReadFile.readHostsFromFile("src/components/resource/data/hostData/host.txt");
            List<Tenant> tenants = TenantReadFile.readTenantsFromFile("src/components/resource/data/tenantData/tenant.txt");
            List<Owner> owners = OwnerReadFile.readOwnersFromFile("src/components/resource/data/ownerData/owner.txt");

            if (hosts != null) {
                personsList.addAll(hosts);
            } else {
                System.out.println("No hosts found.");
            }

            if (tenants != null) {
                personsList.addAll(tenants);
            } else {
                System.out.println("No tenants found.");
            }

            if (owners != null) {
                personsList.addAll(owners);
            } else {
                System.out.println("No owners found.");
            }

        } catch (IOException e) {
            System.err.println("Error loading persons: " + e.getMessage());
            personsList = new ArrayList<>(); // Initialize to avoid null issues
        }
    }
    public List<Person> getPersons() {
        return personsList;
    }

    // Method to get a Host by ID
    public Host getHostById(String id) {
        if (id == null) {
            System.out.println("Provided ID is null.");
            return null;
        }

        for (Person person : personsList) {
            if (person instanceof Host) {
                System.out.println("Checking person with ID: " + person.getId());
                if (person.getId().equals(id)) {
                    System.out.println("Found Host with ID: " + id);
                    return (Host) person;
                }
            }
        }
        System.out.println("Host not found for ID: " + id);
        return null; // Trả về null nếu không tìm thấy
    }
//    public Host getHostById(String id) {
//        for (Person person : personsList) {
//            if (person instanceof Host && person.getId().equals(id)) {
//                return (Host) person;
//            }
//        }
//        return null; // Return null if not found
//    }

    // Method to get a Tenant by ID
    public Tenant getTenantById(String id) {
        for (Person person : personsList) {
            if (person instanceof Tenant && person.getId().equals(id)) {
                return (Tenant) person;
            }
        }
        return null; // Return null if not found
    }

    // Method to get an Owner by ID
    public Owner getOwnerById(String id) {
        for (Person person : personsList) {
            if (person instanceof Owner && person.getId().equals(id)) {
                return (Owner) person;
            }
        }
        return null; // Return null if not found
    }

    // Method to get a Tenant by name
    public Tenant getTenantByName(String name) {
        for (Person person : personsList) {
            if (person instanceof Tenant && person.getFullName().equalsIgnoreCase(name)) {
                return (Tenant) person;
            }
        }
        return null; // Return null if not found
    }

    // Method to get a Host by name
    public Host getHostByName(String name) {
        for (Person person : personsList) {
            if (person instanceof Host && person.getFullName().equalsIgnoreCase(name)) {
                return (Host) person;
            }
        }
        return null; // Return null if not found
    }

    // Method to get an Owner by name
    public Owner getOwnerByName(String name) {
        for (Person person : personsList) {
            if (person instanceof Owner && person.getFullName().equalsIgnoreCase(name)) {
                return (Owner) person;
            }
        }
        return null; // Return null if not found
    }

    // Method to get all persons
    public List<Person> getAllPersons() {
        return new ArrayList<>(personsList);
    }

    public List<Host> getHosts() {
        List<Host> hosts = new ArrayList<>();
        for (Person person : personsList) { // Iterate directly over personsList
            if (person instanceof Host) {
                hosts.add((Host) person);
            }
        }
        return hosts;
    }

    public List<Tenant> getTenants() {
        List<Tenant> tenants = new ArrayList<>();
        for (Person person : personsList) {
            if (person instanceof Tenant) {
                tenants.add((Tenant) person);
            }
        }
        return tenants;
    }

    public List<Owner> getOwners() {
        List<Owner> owners = new ArrayList<>();
        for (Person person : personsList) {
            if (person instanceof Owner) {
                owners.add((Owner) person);
            }
        }
        return owners;
    }

    // Method to display all persons
    public void displayAllPersons() {
        for (Person person : personsList) {
            System.out.println(person);
        }
    }
}