package org.mangorage.mangobotsite.website.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.file.TargetFile;
import org.mangorage.mangobotsite.website.servlet.file.UploadConfig;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.ResolveString;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.UUID;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

@MultipartConfig
public class FileUploadServlet extends StandardHttpServlet {

    private static final ResolveString UPLOADS_DATA = WebServer.WEBPAGE_ROOT.resolve("uploads").resolve("data");
    private static final ResolveString UPLOADS_CONFIGS =  WebServer.WEBPAGE_ROOT.resolve("uploads").resolve("cfg");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processTemplate(
                new MapBuilder(new HashMap<>())
                        .self(this)
                        .get(),
                "file/upload.ftl",
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadId = STR."\{UUID.randomUUID()}";
        HashMap<String, TargetFile> targets = new HashMap<>();
        Integer index = 0;

        for (Part filePart : req.getParts()) {
            String fileId = STR."\{UUID.randomUUID()}";
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";

            targets.put(
                    index.toString(),
                    new TargetFile(
                            index.toString(),
                            fileName,
                            fileId,
                            fileExtension
                    )
            );

            index++;
            // Define the upload directory
            Path filePath = Paths.get(UPLOADS_DATA.value());
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath);
            }

            // Save the file to the upload directory
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath.resolve(fileId), StandardCopyOption.REPLACE_EXISTING);
            }

        }

        Path uploadCfgPath = Paths.get(UPLOADS_CONFIGS.value());
        if (!Files.exists(uploadCfgPath)) {
            Files.createDirectories(uploadCfgPath);
        }

        Files.write(
                uploadCfgPath.resolve(uploadId),
                GSON.toJson(new UploadConfig(uploadId, WebUtil.getOrCreateUserToken(req, resp), targets)).getBytes()
        );

        resp.sendRedirect("/file?id=" + uploadId);
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}