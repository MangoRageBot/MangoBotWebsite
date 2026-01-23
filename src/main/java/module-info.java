module org.mangorage.mangobotwebsite {
    requires freemarker;

    requires org.mangorage.mangobotplugin;
    requires org.mangorage.mangobotcore;
    requires com.google.gson;
    requires org.eclipse.jetty.servlet;
    requires org.jetbrains.annotations;
    requires net.minecraftforge.eventbus;
    requires net.dv8tion.jda;
    requires com.fasterxml.jackson.databind;
    requires org.mangorage.bootstrap;

    // Files
    opens templates;

    // TODO: Deal with it
    exports org.mangorage.mangobotsite.website.servlet.data;

    exports org.mangorage.mangobotsite;
    exports org.mangorage.mangobotsite.website.file;
    exports org.mangorage.mangobotsite.website.servlet.entity;


    exports org.mangorage.mangobotsite.website.filters to org.eclipse.jetty.server;
    exports org.mangorage.mangobotsite.website.servlet to org.eclipse.jetty.server;

    exports org.mangorage.mangobotsite.website to freemarker;
    exports org.mangorage.mangobotsite.website.impl to freemarker;
    exports org.mangorage.mangobotsite.website.util to freemarker;

    exports org.mangorage.mangobotsite.website.servlet.entity.discord to com.google.gson;


    opens org.mangorage.mangobotsite.website.servlet to freemarker, com.google.gson;
    opens org.mangorage.mangobotsite.website.servlet.entity;


    provides org.mangorage.mangobotcore.api.plugin.v1.Plugin with org.mangorage.mangobotsite.MangoBotSite;
    uses org.mangorage.mangobotcore.api.plugin.v1.Plugin;
}