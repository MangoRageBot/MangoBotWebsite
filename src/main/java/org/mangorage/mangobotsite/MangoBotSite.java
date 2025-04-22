package org.mangorage.mangobotsite;


import org.mangorage.mangobotcore.plugin.api.MangoBotPlugin;
import org.mangorage.mangobotcore.plugin.api.Plugin;
import org.mangorage.mangobotcore.plugin.api.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.file.FileUploadManager;
import org.mangorage.mangobotsite.website.impl.ObjectMap;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.nio.file.Path;

@MangoBotPlugin(id = MangoBotSite.ID)
public final class MangoBotSite implements Plugin {
    public static final String ID = "mangobotsite";

    private final FileUploadManager fileUploadManager = new FileUploadManager(Path.of("webpage-root/uploads"));

    public MangoBotSite() {

    }

    public FileUploadManager getFileUploadManager() {
        return fileUploadManager;
    }


    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void load() {
        var pl = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class);

        ObjectMap objectMap = new ObjectMap();
        objectMap.put("trickCommand", pl.getCommandManager().getCommand("trick"));
        objectMap.put("jda", pl.getJDA());
        objectMap.put(WebConstants.FILE_MANAGER, fileUploadManager);

        WebServer.startWebServerSafely(objectMap);
    }
}
