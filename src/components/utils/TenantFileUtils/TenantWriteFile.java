package utils.TenantFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import models.entities.Tenant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenantWriteFile {

    private static final String FILE_PATH = "src/components/resource/data/tenantData/tenant.txt";

    public static void writeTenantToFile(List<Tenant> tenants) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Tenant tenant : tenants) {
                writer.write(tenant.toString());
                writer.newLine();
            }
        }
    }

    public static void updateTenantFile(List<Tenant> updatedTenants, String filePath) throws IOException {
        // Read existing tenants from the file
        List<Tenant> existingTenants = TenantReadFile.readTenantsFromFile();

        // Create a map for quick access to updated tenants
        Map<String, Tenant> updatedTenantMap = new HashMap<>();
        for (Tenant tenant : updatedTenants) {
            updatedTenantMap.put(tenant.getId(), tenant);
        }

        // Write back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Tenant existingTenant : existingTenants) {
                // Check if this tenant needs to be updated
                if (updatedTenantMap.containsKey(existingTenant.getId())) {
                    writer.write(updatedTenantMap.get(existingTenant.getId()).toString());
                } else {
                    writer.write(existingTenant.toString());
                }
                writer.newLine();
            }
        }
    }
}
