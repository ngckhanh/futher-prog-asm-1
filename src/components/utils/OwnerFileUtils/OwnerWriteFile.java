package utils.OwnerFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Owner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OwnerWriteFile {
    public static void writeOwnerToFile(List<Owner> owners, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Owner owner : owners) {
                writer.write(owner.toString());
                writer.newLine();
            }
        }
    }
}
