package utils.HostFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Host;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostWriteFile {
    public static void writeHostToFile(List<Host> hosts, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Host host : hosts) {
                writer.write(host.toString());
                writer.newLine();
            }
        }
    }

    public static void updateHostFile(List<Host> updatedHosts, String filePath) throws IOException {
        // Read existing hosts from the file
        List<Host> existingHosts = HostReadFile.readHostsFromFile();

        // Create a map for quick access to updated hosts
        Map<String, Host> updatedHostMap = new HashMap<>();
        for (Host host : updatedHosts) {
            updatedHostMap.put(host.getId(), host);
        }

        // Write back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Host existingHost : existingHosts) {
                // Check if this host needs to be updated
                if (updatedHostMap.containsKey(existingHost.getId())) {
                    writer.write(updatedHostMap.get(existingHost.getId()).toString());
                } else {
                    writer.write(existingHost.toString());
                }
                writer.newLine();
            }
        }
    }
}