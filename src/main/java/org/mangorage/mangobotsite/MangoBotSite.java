package org.mangorage.mangobotsite;

import org.mangorage.mangobot.MangoBotPlugin;
import org.mangorage.mangobotapi.core.plugin.PluginManager;
import org.mangorage.mangobotapi.core.plugin.api.AddonPlugin;
import org.mangorage.mangobotapi.core.plugin.impl.Plugin;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.impl.ObjectMap;

@Plugin(id = MangoBotSite.ID, type = Plugin.Type.ADDON)
public final class MangoBotSite extends AddonPlugin {
    public static final String ID = "mangobotsite";

    public MangoBotSite() {
        super(ID);

        var pl = PluginManager.getPlugin("mangobot", MangoBotPlugin.class);

        ObjectMap objectMap = new ObjectMap();
        objectMap.put("trickCommand", pl.getCommandRegistry().getCommand("trick"));
        objectMap.put("jda", pl.getJDA());
        WebServer.startWebServerSafely(objectMap);
    }
}
