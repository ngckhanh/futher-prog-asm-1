package utils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import java.util.List;

@FunctionalInterface
public interface EntityDisplay<T> {
    // Abstract method to display a list of entities
    void display(List<T> entities);
}