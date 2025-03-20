package org.mangorage.mangobotsite.website.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class FileUploadManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path CONFIG_PATH;
    private final Path DATA_PATH;

    public FileUploadManager(Path rootPath) {
        this.CONFIG_PATH = rootPath.resolve("config");
        this.DATA_PATH = rootPath.resolve("data");
    }

    public UploadConfig getUploadConfig(String id) {
        Path file = CONFIG_PATH.resolve(id);
        if (Files.exists(file)) {
            try (FileReader reader = new FileReader(file.toFile())) {
                return GSON.fromJson(reader, UploadConfig.class);
            } catch (IOException ignored) {
                return null;
            }
        }
        return null;
    }

    public Path getTargetPath(TargetFile targetFile) {
        return DATA_PATH.resolve(targetFile.path());
    }

    public void saveUpload(UploadConfig config) throws IOException {
        Files.write(
                CONFIG_PATH.resolve(config.id()),
                GSON.toJson(config).getBytes()
        );
    }

    public void deleteUpload(UploadConfig config) {
        config.delete(CONFIG_PATH, DATA_PATH);
    }

    public void deleteTarget(TargetFile targetFile) {
        targetFile.delete(DATA_PATH);
    }

    public String createUpload(List<FileStream> fileStreams, String accountId) throws IOException {
        String uploadId = UUID.randomUUID().toString();
        HashMap<String, TargetFile> targets = new HashMap<>();
        int index = 0;

        for (FileStream filePart : fileStreams) {
            String fileId = UUID.randomUUID().toString();
            String fileName = Paths.get(filePart.getFileName()).getFileName().toString();
            String fileExtension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";

            targets.put(
                    Integer.toString(index),
                    new TargetFile(
                            index + "",
                            fileName,
                            fileId,
                            fileExtension
                    )
            );

            index++;
            // Define the upload directory

            if (!Files.exists(DATA_PATH)) {
                Files.createDirectories(DATA_PATH);
            }

            // Save the file to the upload directory
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, DATA_PATH.resolve(fileId), StandardCopyOption.REPLACE_EXISTING);
            }

        }

        if (!Files.exists(CONFIG_PATH)) {
            Files.createDirectories(CONFIG_PATH);
        }

        Files.write(
                CONFIG_PATH.resolve(uploadId),
                GSON.toJson(new UploadConfig(uploadId, accountId, targets)).getBytes()
        );


        return uploadId;
    }
}
