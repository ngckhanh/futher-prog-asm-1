package utils.PropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.Property;
import models.entities.ResidentialProperty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PropertyWriteFile {
    public static void writePropertyToFile(List<Property> properties, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Property property : properties) {
                writer.write(property.toString());
                writer.newLine();
            }
        }
    }
}
