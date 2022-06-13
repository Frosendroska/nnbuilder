package org.hse.nnbuilder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.apache.commons.codec.digest.DigestUtils;
import org.hse.nnbuilder.exception.HashError;
import org.jetbrains.annotations.NotNull;

/**
 * Hashing of file and hash of a String using sha1Hex
 */
public class HashUtil {
    public static String getFileHash(@NotNull Path filePath) {
        try (InputStream is = Files.newInputStream(filePath)) {
            return DigestUtils.sha3_512Hex(is);
        } catch (IOException e) {
            throw new HashError("Impossible to hash " + filePath.getFileName() + ".", e);
        }
    }

    public static String getStringHash(@NotNull String commitInfo) {
        return DigestUtils.sha1Hex(commitInfo);
    }

    public static String setFileHash(Path file, Path storage) {
        try {
            String hash = getFileHash(file);
            Path copied = storage.resolve(hash);
            Files.copy(file, copied, StandardCopyOption.REPLACE_EXISTING);
            return hash;
        } catch (IOException e) {
            throw new HashError("Impossible to set file hash.", e);
        }
    }
}
