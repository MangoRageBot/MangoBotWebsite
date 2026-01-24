package org.mangorage.mangobotsite.website.servlet.data.trick;

import org.mangorage.mangobotplugin.commands.trick.Trick;
import org.mangorage.mangobotplugin.commands.trick.TrickManager;

public final class TrickInfoData {
    private final Trick trick;

    public TrickInfoData(Trick trick) {
        this.trick = trick;
    }

    public static Object get(TrickManager manager, long guildId) {
        return manager.getTricksForGuild(guildId).stream()
                .map(TrickInfoData::new)
                .toList();
    }

    public String getId() {
        return trick.getTrickID();
    }

    public String getName() {
        return getId();
    }

    public String getType() {
        return trick.getType() == null ? "Invalid" : trick.getType().name();
    }
}
