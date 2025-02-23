package org.mangorage.mangobotsite.website.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.file.FileStream;
import org.mangorage.mangobotsite.website.file.FileUploadManager;
import org.mangorage.mangobotsite.website.impl.ObjectMap;
import org.mangorage.mangobotsite.website.impl.StandardHttpServlet;
import org.mangorage.mangobotsite.website.util.MapBuilder;
import org.mangorage.mangobotsite.website.util.WebConstants;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.util.HashMap;

import static org.mangorage.mangobotsite.website.util.WebUtil.processTemplate;

@MultipartConfig
public class FileUploadServlet extends StandardHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processTemplate(
                MapBuilder.of()
                        .self(this)
                        .get(),
                "file/upload.ftl",
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMap map = (ObjectMap) getServletContext().getAttribute(WebConstants.WEB_OBJECT_ID);

        FileUploadManager manager = map.get(WebConstants.FILE_MANAGER, FileUploadManager.class);
        if (req.getParts().isEmpty()) return;
        var id = manager.createUpload(
                req
                        .getParts()
                        .stream()
                        .map(part -> new FileStream(part.getSubmittedFileName(), part::getInputStream))
                        .toList(),
                WebUtil.getOrCreateUserToken(req, resp)
        );

        resp.sendRedirect("/file?id=" + id);
    }

    @Override
    public boolean useDefaultStyles() {
        return false;
    }
}