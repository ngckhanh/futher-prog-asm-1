package utils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */

import java.util.Comparator;

public class SortOption<T> {
    private String description;
    private Comparator<T> comparator;

    public SortOption(String description, Comparator<T> comparator) {
        this.description = description;
        this.comparator = comparator;
    }

    public String getDescription() {
        return description;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }
}
