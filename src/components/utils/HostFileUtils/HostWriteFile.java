package utils.HostFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Host;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class HostWriteFile {
    public static void writeHostToFile(List<Host> hosts, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Host host : hosts) {
                writer.write(host.toString());
                writer.newLine();
            }
        }
    }
}
