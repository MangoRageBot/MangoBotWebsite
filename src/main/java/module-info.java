module org.mangorage.mangobotwebsite {
    requires freemarker;

    requires org.mangorage.mangobotplugin;
    requires org.mangorage.mangobotcore;
    requires com.google.gson;
    requires org.eclipse.jetty.servlet;
    requires annotations;

    exports org.mangorage.mangobotsite;
    exports org.mangorage.mangobotsite.website.file;
}