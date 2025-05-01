module org.mangorage.mangobotwebsite {
    requires freemarker;

    requires org.mangorage.mangobotplugin;
    requires org.mangorage.mangobotcore;
    requires com.google.gson;
    requires org.eclipse.jetty.servlet;
    requires org.jetbrains.annotations;

    exports org.mangorage.mangobotsite;
    exports org.mangorage.mangobotsite.website.file;

    exports org.mangorage.mangobotsite.website.filters to org.eclipse.jetty.server;
    exports org.mangorage.mangobotsite.website.servlet to org.eclipse.jetty.server;

    exports org.mangorage.mangobotsite.website to freemarker;
    exports org.mangorage.mangobotsite.website.impl to freemarker;
    exports org.mangorage.mangobotsite.website.util to freemarker;

    opens org.mangorage.mangobotsite.website.servlet to freemarker;

    provides org.mangorage.mangobotcore.plugin.api.Plugin with org.mangorage.mangobotsite.MangoBotSite;
    uses org.mangorage.mangobotcore.plugin.api.Plugin;
}