package org.mangorage.mangobotsite;

import org.mangorage.mangobot.MangoBotPlugin;
import org.mangorage.mangobotapi.core.plugin.AbstractPlugin;
import org.mangorage.mangobotapi.core.plugin.PluginManager;
import org.mangorage.mangobotapi.core.plugin.impl.Plugin;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.file.FileUploadManager;
import org.mangorage.mangobotsite.website.impl.ObjectMap;
import org.mangorage.mangobotsite.website.util.WebConstants;

import java.nio.file.Path;

@Plugin(id = MangoBotSite.ID)
public final class MangoBotSite extends AbstractPlugin {
    public static final String ID = "mangobotsite";

    private final FileUploadManager fileUploadManager = new FileUploadManager(Path.of("webpage-root/uploads"));

    public MangoBotSite() {

    }

    public FileUploadManager getFileUploadManager() {
        return fileUploadManager;
    }

    @Override
    protected void init() {
        var pl = PluginManager.getPlugin("mangobot", MangoBotPlugin.class);

        ObjectMap objectMap = new ObjectMap();
        objectMap.put("trickCommand", pl.getCommandRegistry().getCommand("trick"));
        objectMap.put("jda", pl.getJDA());
        objectMap.put(WebConstants.FILE_MANAGER, fileUploadManager);

        WebServer.startWebServerSafely(objectMap);
    }
}
