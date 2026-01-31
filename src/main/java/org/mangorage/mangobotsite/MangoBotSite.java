package org.mangorage.mangobotsite;


import org.mangorage.mangobotcore.api.plugin.v1.MangoBotPlugin;
import org.mangorage.mangobotcore.api.plugin.v1.Plugin;
import org.mangorage.mangobotcore.api.plugin.v1.PluginManager;
import org.mangorage.mangobotplugin.entrypoint.MangoBot;
import org.mangorage.mangobotsite.website.WebServer;
import org.mangorage.mangobotsite.website.util.ObjectMap;

@MangoBotPlugin(id = MangoBotSite.ID)
public final class MangoBotSite implements Plugin {
    public static final String ID = "mangobotsite";

    public MangoBotSite() {
    }


    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void load() {
        var pl = PluginManager.getInstance().getPlugin("mangobot").getInstance(MangoBot.class);

        ObjectMap objectMap = new ObjectMap();
        objectMap.put("trickManager", pl.getTrickManager());
        objectMap.put("jda", pl.getJDA());

        WebServer.startWebServerSafely(objectMap);
    }
}
