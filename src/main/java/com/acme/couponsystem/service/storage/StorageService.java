package com.acme.couponsystem.service.storage;

import com.acme.couponsystem.db.storage.StorageConfig;
import com.acme.couponsystem.service.storage.ex.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    private static final int FILE_NAME_LENGTH = 16;
    private static final int EXTENSION = 1;

    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String GIF = "gif";

    private final Path fileStorageLocation;

    @Autowired
    public StorageService(StorageConfig storageConfig) {
        this.fileStorageLocation = createPath(storageConfig.getDirectory());
        /*create the location where the file will be stored if non-existent*/
        createFileStorageLocation(this.fileStorageLocation);
    }

    public String storeImage(MultipartFile file, String path) {

        /*create the location where the file will be stored if non-existent*/
        createFileStorageLocation(this.fileStorageLocation.resolve(path));

        String fileExtension = getFileExtension(file.getContentType());
        validateImageFormat(fileExtension);

        String fileName = createUUID() + "." + fileExtension;
        String fileLocation = path + "/" + fileName;

        Path targetLocation = this.fileStorageLocation.resolve(fileLocation);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store image" + ex.getMessage());
        }

        return fileLocation;
    }

    public boolean deleteFile(String profilePictureUri) {
        boolean success;
        try {
            success = Files.deleteIfExists(this.fileStorageLocation.resolve(profilePictureUri));
        } catch (IOException e) {
            throw new FileStorageException("Unable to delete old image" + e.getMessage());
        }
        return success;
    }

    private String getFileExtension(String contentType) {
        if (Objects.isNull(contentType)) {
            throw new FileStorageException("The file must have a format");
        }
        return contentType.split("/")[EXTENSION];
    }

    private void validateImageFormat(String contentType) {

        switch (contentType.toLowerCase()) {
            case JPEG:
            case PNG:
            case GIF:
                return;
            default:
                String message = String.format("The file format must be: %s, %s or %s", JPEG, PNG, GIF);
                throw new FileStorageException(message);
        }
    }

    private String createUUID() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, FILE_NAME_LENGTH);
    }

    private Path createPath(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }

    private Path createFileStorageLocation(Path path) {
        try {
            return Files.createDirectories(path);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored." + ex.getMessage());
        }
    }


}
