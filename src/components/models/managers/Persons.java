package models.managers;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.Host;
import models.entities.Owner;
import models.entities.Person;
import models.entities.Tenant;
import utils.HostFileUtils.HostReadFile;
import utils.OwnerFileUtils.OwnerReadFile;
import utils.TenantFileUtils.TenantReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persons {
    private ArrayList<Person> personsList;

    public Persons() {
        this.personsList = new ArrayList<>();
        loadFromFile();
    }

    // Method to load persons from file
    public void loadFromFile() {
        try {
            List<Host> hosts = HostReadFile.readHostsFromFile();
            List<Tenant> tenants = TenantReadFile.readTenantsFromFile();
            List<Owner> owners = OwnerReadFile.readOwnersFromFile();

            personsList.addAll(hosts);
            personsList.addAll(tenants);
            personsList.addAll(owners);

        } catch (IOException e) {
            System.err.println("Error loading persons: " + e.getMessage());
            personsList = new ArrayList<>(); // Initialize to avoid null issues
        }
    }

    // Method to get a list of Owner names
    public List <String> getOwnerNames() {
        List<String> ownerNames = new ArrayList<>();
        for (Person person : personsList) {
            if (person instanceof Owner) {
                ownerNames.add(person.getFullName());
            }
        }
        return ownerNames;
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

}