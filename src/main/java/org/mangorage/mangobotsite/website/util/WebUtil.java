package org.mangorage.mangobotsite.website.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public final class WebUtil {
    private static final Configuration CONFIG;

    static {
        CONFIG = new Configuration(
                new Version("2.3.31")
        );

        CONFIG.setTemplateLoader(
                new ClassTemplateLoader(
                        WebUtil.class.getClassLoader(),
                        "templates"
                )
        );
        CONFIG.setDefaultEncoding("UTF-8");
    }

    public static @Nullable TemplateException processTemplate(Map<String, Object> data, String templateURL, Writer writer) throws IOException {;

        try {
            CONFIG.getTemplate(templateURL).process(data, writer);
        } catch (TemplateException e) {
            return e;
        }

        return null;
    }
}
