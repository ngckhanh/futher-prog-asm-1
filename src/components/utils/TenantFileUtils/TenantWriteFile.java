package utils.TenantFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Tenant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TenantWriteFile {

    private static final String FILE_PATH = "data/tenant.txt";

    /**
     * Writes a list of tenants to the specified file.
     *
     * @param tenants The list of tenants to be written.
     * @throws IOException If an I/O error occurs.
     */
    public void writeTenantToFile(List<Tenant> tenants) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Tenant tenant : tenants) {
                writer.write(tenant.toString());
                writer.newLine();
            }
        }
    }
}
