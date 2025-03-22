package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.file.FileUploadManager;
import org.mangorage.mangobotsite.website.impl.ObjectMap;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.file.TargetFile;
import org.mangorage.mangobotsite.website.file.UploadConfig;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

public class FileServlet extends StandardHttpServlet {

    private static final Map<String, String> EXTENSIONS = new HashMap<>();

    private static void put(String type, List<String> extensions) {
        extensions.forEach(ext -> EXTENSIONS.put(ext, type));
    }

    static {
        // Images
        put(
                "image/jpeg",
                List.of(
                        ".jpg",
                        ".jpeg"
                )
        );

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
        put(
                "text/plain",
                List.of(
                        ".txt",
                        ".log"
                )
        );
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
        ObjectMap map = (ObjectMap) getServletContext().getAttribute(WebConstants.WEB_OBJECT_ID);
        FileUploadManager manager = map.get(WebConstants.FILE_MANAGER, FileUploadManager.class);

        // Retrieve parameters
        String id = request.getParameter("id");
        String target = request.getParameter("target"); // optional
        String download = request.getParameter("dl");     // optional
        String delete = request.getParameter("delete");     // optional

        if (id == null || id.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File ID is required.");
            return;
        }

        UploadConfig config = manager.getUploadConfig(id);
        if (config == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid File ID");
            return;
        }

        boolean isOwner = config.isAccount(request, response);
        boolean header = request.getParameter("header") != null;

        // Insufficient permission to delete
        if (!isOwner && delete != null) {
            processTemplate(
                    MapBuilder.of()
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
                    manager.deleteTarget(targetFile);
                    manager.saveUpload(config);
                } else {
                    processTemplate(
                            MapBuilder.of()
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
                manager.deleteUpload(config);
            }

            processTemplate(
                    MapBuilder.of()
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
                    MapBuilder.of()
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
                handleFileRequest(manager, targetFile, id, false, header, response, request);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Target");
            }
        } else {
            // Download file
            TargetFile targetFile = config.targets().get(target);
            if (targetFile != null) {
                handleFileRequest(manager, targetFile, id, true, false, response, request);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Target");
                return;
            }
        }
    }

    private void handleFileRequest(FileUploadManager manager, TargetFile targetFile, String id, boolean download, boolean header, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (header) {
            response.setContentType("text/html");
            processTemplate(
                    MapBuilder.of()
                            .self(this)
                            .put("title", "MangoBot Upload")
                            .put("contentURL", "https://mangobot.mangorage.org/file?id=%s&target=%s".formatted(id, targetFile.index()))
                            .put("url", "https://mangobot.mangorage.org/file?id=%s&target=%s".formatted(id, targetFile.index()))
                            .get(),
                    "general/embed.ftl",
                    response.getWriter()
            );
        } else {
            File file = manager.getTargetPath(targetFile).toFile();

            if (file.exists() && file.isFile()) {
                String contentType = download ? "application/octet-stream" : EXTENSIONS.getOrDefault(targetFile.extension(), "text/plain");
                boolean isTextFile = contentType.contains("text");
                boolean isVideoFile = contentType.startsWith("video");

                if (isTextFile) {
                    response.setContentType("text/html");
                    List<String> text = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            text.add(line);
                        }
                    }

                    processTemplate(
                            MapBuilder.of()
                                    .put("lines", text)
                                    .put("name", targetFile.name())
                                    .get(),
                            "file.ftl",
                            response.getWriter()
                    );

                } else if (isVideoFile) {
                    response.setContentType(contentType);

                    // Optional but recommended headers for video streaming:
                    response.setHeader("Accept-Ranges", "bytes"); // Allow range requests for seeking
                    // Caching headers (adjust as needed):
                    response.setHeader("Cache-Control", "public, max-age=3600"); // Example: Cache for 1 hour

                    String range = request.getHeader("Range");
                    if (range != null) {
                        handleVideoRangeRequest(file, range, response);
                    } else {
                        // If no range is specified, send the whole video content
                        response.setContentLengthLong(file.length());
                        try (InputStream fileInputStream = new FileInputStream(file)) {
                            fileInputStream.transferTo(response.getOutputStream());
                        }
                    }

                } else {
                    response.setContentType(contentType);
                    if (download) {
                        response.setHeader("Content-Disposition", "attachment; filename=\"%s\"".formatted(targetFile.name()));
                    }
                    try (InputStream fileInputStream = new FileInputStream(file)) {
                        fileInputStream.transferTo(response.getOutputStream());
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            }
        }
    }

    private void handleVideoRangeRequest(File file, String range, HttpServletResponse response) throws IOException {
        // Parsing range
        String[] ranges = range.replace("bytes=", "").split("-");
        long start = Long.parseLong(ranges[0]);
        long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : file.length() - 1;

        // Ensure the range is valid
        if (start >= file.length() || end >= file.length() || start > end) {
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            return;
        }

        // Set the appropriate headers for partial content
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentType("video/mp4");
        response.setContentLengthLong(end - start + 1);
        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + file.length());

        // Send the video content for the range
        try (RandomAccessFile videoFile = new RandomAccessFile(file, "r")) {
            videoFile.seek(start);
            byte[] buffer = new byte[8192]; // Buffer size
            int bytesRead;
            while ((bytesRead = videoFile.read(buffer)) != -1) {
                if (start + bytesRead > end) {
                    bytesRead = (int) (end - start + 1);
                }
                response.getOutputStream().write(buffer, 0, bytesRead);
                start += bytesRead;
                if (start > end) {
                    break;
                }
            }
        }
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}
