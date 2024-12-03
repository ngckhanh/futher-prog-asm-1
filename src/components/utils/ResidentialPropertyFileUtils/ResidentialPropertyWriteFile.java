package utils.ResidentialPropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Property;
import models.entities.ResidentialProperty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ResidentialPropertyWriteFile {
    public static void writeResidentialPropertiesToFile(String fileName, List<ResidentialProperty> residentialProperties) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Property residentialProperty : residentialProperties) {
                writer.write(residentialProperty.toString());
                writer.newLine();
            }
        }
    }
}
