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
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty or null file.");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new IllegalArgumentException("Original filename is empty");
            }

            // Generate unique filename
            String filename = UUID.randomUUID().toString() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
            Path destinationFile = roomsPath.resolve(filename);

            logger.info("Storing file: {} to path: {}", filename, destinationFile);

            // Copy file to destination
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Verify file was created
            if (!Files.exists(destinationFile)) {
                throw new IOException("Failed to verify file creation: " + destinationFile);
            }

            logger.info("Successfully stored file: {}", filename);
            return filename;
        } catch (IOException e) {
            String errorMsg = "Failed to store file: " + e.getMessage();
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
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