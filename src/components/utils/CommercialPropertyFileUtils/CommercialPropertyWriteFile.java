package utils.CommercialPropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.CommercialProperty;
import models.entities.Property;
import models.entities.ResidentialProperty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CommercialPropertyWriteFile {
    public static void writeCommercialPropertiesToFile(String fileName, List<CommercialProperty> commercialProperties) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Property commercialProperty : commercialProperties) {
                writer.write(commercialProperty.toString());
                writer.newLine();
            }
        }
    }


}
