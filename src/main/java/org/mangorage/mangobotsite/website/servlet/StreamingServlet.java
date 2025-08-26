package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;


public class StreamingServlet extends StandardHttpServlet {

    private final Path videoDirectory = Path.of("stream");



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoName = req.getParameter("v");
        String mode = req.getParameter("mode"); // "stream" means serve bytes

        if (videoName == null || videoName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing video parameter");
            return;
        }

        File videoFile = videoDirectory.resolve(videoName).toFile();
        if (!videoFile.exists() || !videoFile.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Video not found");
            return;
        }

        if ("stream".equals(mode)) {
            System.out.println("STREAM");
            streamVideo(req, resp, videoFile);
        } else {
            resp.setContentType("text/html");
            processTemplate(
                    MapBuilder.of()
                            .self(this)
                            .put("videoName", videoName)
                            .put("title", videoFile.getName())
                            .get(),
                    "stream.ftl",
                    resp.getWriter()
            );
        }
    }

    private void streamVideo(HttpServletRequest req, HttpServletResponse resp, File videoFile) throws IOException {
        long fileLength = videoFile.length();
        String range = req.getHeader("Range");

        String contentType = Files.probeContentType(videoFile.toPath());
        if (contentType == null) contentType = "video/mp4";

        resp.setHeader("Accept-Ranges", "bytes");
        resp.setContentType(contentType);

        try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r");
             OutputStream out = resp.getOutputStream()) {

            if (range != null && range.startsWith("bytes=")) {
                String[] parts = range.substring(6).split("-");
                long start = 0;
                long end = fileLength - 1;

                try {
                    start = Long.parseLong(parts[0]);
                    if (parts.length > 1 && !parts[1].isEmpty()) {
                        end = Long.parseLong(parts[1]);
                    }
                } catch (NumberFormatException ignored) {
                }

                if (start > end || start < 0 || end >= fileLength) {
                    resp.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                    resp.setHeader("Content-Range", "bytes */" + fileLength);
                    return;
                }

                long contentLength = end - start + 1;
                resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
                resp.setContentLengthLong(contentLength);

                raf.seek(start);
                writeStream(raf, out, contentLength);
            } else {
                resp.setContentLengthLong(fileLength);
                writeStream(raf, out, fileLength);
            }
            out.flush();
        }
    }

    private void writeStream(RandomAccessFile raf, OutputStream out, long length) throws IOException {
        byte[] buffer = new byte[8192];
        long remaining = length;

        while (remaining > 0) {
            int read = raf.read(buffer, 0, (int) Math.min(buffer.length, remaining));
            if (read == -1) break;
            out.write(buffer, 0, read);
            remaining -= read;
        }
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}