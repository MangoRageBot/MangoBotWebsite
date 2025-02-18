package org.mangorage.mangobotsite.website.servlet.file;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mangorage.mangobotsite.website.util.WebUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public record UploadConfig
        (
                String id,
                String account_id,
                HashMap<String, TargetFile> targets
        )
{

        public void delete(Path cfgPath, Path dataPath) {
            try {
                Files.deleteIfExists(
                        cfgPath.resolve(id)
                );
                targets.forEach((k, t) -> t.delete(dataPath));
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }

        public List<TargetFile> targetList() {
            return targets.values().stream().toList();
        }

        public boolean isAccount(HttpServletRequest request, HttpServletResponse response) {
                var id = WebUtil.getOrCreateUserToken(request, response);
                return id.equals(account_id);
        }
}
