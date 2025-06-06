package com.nexastay.roomservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${upload.path:uploads/rooms/}")
    private String uploadPath;

    @Value("${upload.base-url}")
    private String baseUrl;

    private final Path root;
    private final Path roomsPath;

    public FileStorageService() {
        this.root = Paths.get("uploads");
        this.roomsPath = root.resolve("rooms");
        initializeDirectories();
    }

    private void initializeDirectories() {
        try {
            if (!Files.exists(root)) {
                logger.info("Creating root upload directory: {}", root);
                Files.createDirectories(root);
            }
            if (!Files.exists(roomsPath)) {
                logger.info("Creating rooms upload directory: {}", roomsPath);
                Files.createDirectories(roomsPath);
            }
            // Test write permissions
            String testFile = "test_" + UUID.randomUUID() + ".tmp";
            Path testPath = roomsPath.resolve(testFile);
            Files.createFile(testPath);
            Files.delete(testPath);
            logger.info("Successfully verified write permissions in upload directories");
        } catch (IOException e) {
            String errorMsg = "Could not initialize upload folders. Please check directory permissions.";
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    public String storeRoomImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file");
            }

            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // Save the file
            Path destinationFile = roomsPath.resolve(filename);
            logger.info("Storing file at: {}", destinationFile);

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Successfully stored file: {}", filename);

            return filename;
        } catch (IOException e) {
            String errorMsg = "Failed to store file: " + e.getMessage();
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    public String getImageUrl(String filename) {
        return baseUrl + filename;
    }

    public boolean deleteRoomImage(String filename) {
        try {
            Path file = roomsPath.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            logger.error("Error deleting file: {}", filename, e);
            return false;
        }
    }
}