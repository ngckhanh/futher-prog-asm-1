package models.entities;

public class test {
//    public Map<String, Host> readHostsFirstTime(){
//        Map<String, Host> hostsMap = new LinkedHashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(hostsFilePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                String id = data[0].trim();
//                String fullName = data[1].trim();
//                String dobString = data[2].trim();
//                String contactInfo = data[3].trim();
//                Date dob = null;
//                try {
//                    dob = dateFormat.parse(dobString);
//                } catch (ParseException e) {
//                    System.err.println("Invalid date format for owner: " + dobString);
//                }
//                Host host = new Host(id, fullName, dob, contactInfo);
//                hostsMap.put(id, host);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return hostsMap;
//    }
//
//    public Map<String, Host> readHostsSecondTime(Map<String, Host> hostsMap, Map<String, Owner> ownersMap, Map<String, Property> propertiesMap, Map<String, RentalAgreement> rentalAgreementMap) {
//        try (BufferedReader br = new BufferedReader(new FileReader(hostsFilePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                String id = data[0].trim();
//                Host host = hostsMap.get(id);
//                if (host == null) {
//                    System.err.println("Host not found for ID: " + id);
//                    continue;
//                }
//
//                String[] managedPropertiesId = data[4].trim().isEmpty() ? new String[0] : data[4].trim().split(" ");
//                String[] cooperatingOwnersId = data[5].trim().isEmpty() ? new String[0] : data[5].trim().split(" ");
//                String[] rentalAgreementsId = data[6].trim().isEmpty() ? new String[0] : data[6].trim().split(" ");
//
//                List<Property> propertiesList = new ArrayList<>();
//                for (String propertyId : managedPropertiesId) {
//                    Property property = propertiesMap.get(propertyId);
//                    if (property != null) {
//                        propertiesList.add(property);
//                    } else {
//                        System.err.println("Property not found for ID: " + propertyId);
//                    }
//                }
//
//                List<Owner> ownerList = new ArrayList<>();
//                for (String ownerId : cooperatingOwnersId) {
//                    Owner owner = ownersMap.get(ownerId);
//                    if (owner != null) {
//                        ownerList.add(owner);
//                    } else {
//                        System.err.println("Owner not found for ID: " + ownerId);
//                    }
//                }
//
//                List<RentalAgreement> rentalAgreementList = new ArrayList<>();
//                for (String rentalAgreementId : rentalAgreementsId) {
//                    RentalAgreement rentalAgreement = rentalAgreementMap.get(rentalAgreementId);
//                    if (rentalAgreement != null) {
//                        rentalAgreementList.add(rentalAgreement);
//                    } else {
//                        System.err.println("Rental Agreement not found for ID: " + rentalAgreementId);
//                    }
//                }
//
//                host.setManagedProperties(propertiesList);
//                host.setCooperatingOwners(ownerList);
//                host.setRentalAgreements(rentalAgreementList);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return hostsMap;
//    }

}
