package org.hse.nnbuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class DatasetUtil {

    private static String RESOURCE_ROOT = "src/test/resources";

    private DatasetUtil() {}

    private static byte[] readResourceFile(String pathname) throws IOException {
        return Files.readAllBytes(Paths.get(RESOURCE_ROOT, pathname));
    }

    public static byte[] readDatasetFile() throws IOException {
        return readResourceFile("2022_forbes_billionaires.csv");
    }
}
