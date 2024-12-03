package utils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface EntitySave<T> {
    // Abstract method to save a list of entities
    void save(List<T> entities) throws IOException;
}