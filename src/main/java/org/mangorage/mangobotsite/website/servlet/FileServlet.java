package org.mangorage.mangobotsite.website.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.servlet.file.TargetFile;
import org.mangorage.mangobotsite.website.servlet.file.UploadConfig;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.ResolveString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

public class FileServlet extends StandardHttpServlet {

    private static final ResolveString UPLOADS_DATA = WebServer.WEBPAGE_ROOT.resolve("uploads").resolve("data");
    private static final ResolveString UPLOADS_CONFIGS = WebServer.WEBPAGE_ROOT.resolve("uploads").resolve("cfg");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Map<String, String> EXTENSIONS = new HashMap<>();

    static {
        // Images
        EXTENSIONS.put(".jpg", "image/jpeg");
        EXTENSIONS.put(".jpeg", "image/jpeg");
        EXTENSIONS.put(".png", "image/png");
        EXTENSIONS.put(".gif", "image/gif");
        EXTENSIONS.put(".bmp", "image/bmp");
        EXTENSIONS.put(".webp", "image/webp");
        EXTENSIONS.put(".ico", "image/x-icon");
        EXTENSIONS.put(".svg", "image/svg+xml");
        EXTENSIONS.put(".tif", "image/tiff");
        EXTENSIONS.put(".tiff", "image/tiff");

        // Documents
        EXTENSIONS.put(".pdf", "application/pdf");
        EXTENSIONS.put(".txt", "text/plain");
        EXTENSIONS.put(".csv", "text/csv");
        EXTENSIONS.put(".json", "application/json");
        EXTENSIONS.put(".xml", "application/xml");

        // Audio
        EXTENSIONS.put(".mp3", "audio/mpeg");
        EXTENSIONS.put(".wav", "audio/wav");
        EXTENSIONS.put(".ogg", "audio/ogg");
        EXTENSIONS.put(".flac", "audio/flac");
        EXTENSIONS.put(".aac", "audio/aac");

        // Video
        EXTENSIONS.put(".mp4", "video/mp4");
        EXTENSIONS.put(".avi", "video/x-msvideo");
        EXTENSIONS.put(".mov", "video/quicktime");
        EXTENSIONS.put(".wmv", "video/x-ms-wmv");
        EXTENSIONS.put(".flv", "video/x-flv");
        EXTENSIONS.put(".webm", "video/webm");

        // Fonts
        EXTENSIONS.put(".ttf", "font/ttf");
        EXTENSIONS.put(".otf", "font/otf");
        EXTENSIONS.put(".woff", "font/woff");
        EXTENSIONS.put(".woff2", "font/woff2");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters
        String id = request.getParameter("id");
        String target = request.getParameter("target"); // optional
        String download = request.getParameter("dl");     // optional
        String delete = request.getParameter("delete");     // optional

        if (id == null || id.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File ID is required.");
            return;
        }

        UploadConfig config = fetchConfig(id);
        if (config == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid File ID");
            return;
        }

        boolean isOwner = config.isAccount(request, response);
        boolean header = request.getParameter("header") != null;

        // Insufficient permission to delete
        if (!isOwner && delete != null) {
            processTemplate(
                    new MapBuilder(new HashMap<>())
                            .self(this)
                            .put("url", "/file?id=" + id)
                            .put("title", "Insufficient Permission")
                            .put("message", "Insufficient Permission")
                            .get(),
                    "general/redirect.ftl",
                    response.getWriter()
            );
            return;
        }

        // Delete action
        if (isOwner && delete != null) {
            if (target != null) {
                TargetFile targetFile = config.targets().get(target);
                if (targetFile != null) {
                    config.targets().remove(target);
                    Path uploadCfgPath = Paths.get(UPLOADS_CONFIGS.value());
                    Path dataPath = Paths.get(UPLOADS_DATA.value());
                    targetFile.delete(dataPath);
                    if (!Files.exists(uploadCfgPath)) {
                        Files.createDirectories(uploadCfgPath);
                    }
                    Files.write(uploadCfgPath.resolve(id), GSON.toJson(config).getBytes());
                } else {
                    processTemplate(
                            new MapBuilder(new HashMap<>())
                                    .self(this)
                                    .put("url", "/file?id=" + id)
                                    .put("title", "Invalid Target")
                                    .put("message", "Invalid Target")
                                    .get(),
                            "general/redirect.ftl",
                            response.getWriter()
                    );
                    return;
                }
            } else {
                Path uploadCfgPath = Paths.get(UPLOADS_CONFIGS.value());
                Path dataPath = Paths.get(UPLOADS_DATA.value());
                config.delete(uploadCfgPath, dataPath);
            }

            processTemplate(
                    new MapBuilder(new HashMap<>())
                            .self(this)
                            .put("title", "Deleted Target")
                            .put("message", "Deleted File")
                            .put("url", "/file?id=" + id)
                            .get(),
                    "general/redirect.ftl",
                    response.getWriter()
            );

            return;
        }

        // Display file management page if no specific target is provided
        if (target == null) {
            processTemplate(
                    new MapBuilder(new HashMap<>())
                            .self(this)
                            .put("id", id)
                            .put("isOwner", isOwner)
                            .put("config", config)
                            .get(),
                    "file/files.ftl",
                    response.getWriter()
            );
            return;
        } else if (download == null) {
            // Display file inline
            TargetFile targetFile = config.targets().get(target);
            if (targetFile != null) {
                handleFileRequest(targetFile, id, false, header, response);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Target");
            }
        } else {
            // Download file
            TargetFile targetFile = config.targets().get(target);
            if (targetFile != null) {
                handleFileRequest(targetFile, id, true, false, response);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Target");
                return;
            }
        }
    }

    private UploadConfig fetchConfig(String id) {
        Path file = Paths.get(UPLOADS_CONFIGS.value()).resolve(id);
        if (Files.exists(file)) {
            try (FileReader reader = new FileReader(file.toFile())) {
                return GSON.fromJson(reader, UploadConfig.class);
            } catch (IOException ignored) {
                return null;
            }
        }
        return null;
    }

    private void handleFileRequest(TargetFile targetFile, String id, boolean download, boolean header, HttpServletResponse response) throws IOException {

        if (header) {
            response.setContentType("text/html");
            processTemplate(
                    new MapBuilder(new HashMap<>())
                            .self(this)
                            .put("title", "MangoBot Upload")
                            .put("contentURL", "https://mangobot.mangorage.org/file?id=%s&target=%s".formatted(id, targetFile.index()))
                            .get(),
                    "general/header.ftl",
                    response.getWriter()
            );
        } else {
            File file = new File(UPLOADS_DATA.value(), targetFile.path());

            if (file.exists() && file.isFile()) {
                String contentType = download ? "application/octet-stream"
                        : EXTENSIONS.getOrDefault(targetFile.extension(), "text/plain");
                response.setContentType(contentType);
                response.setContentLengthLong(file.length());
                if (download) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + targetFile.name() + "\"");
                }
                try (InputStream fileInputStream = new FileInputStream(file)) {
                    fileInputStream.transferTo(response.getOutputStream());
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            }
        }
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}
