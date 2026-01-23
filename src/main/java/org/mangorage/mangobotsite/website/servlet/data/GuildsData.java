package org.mangorage.mangobotsite.website.servlet.data;

import net.dv8tion.jda.api.JDA;

import java.util.List;
import java.util.Objects;

public record GuildsData(
        String getId,
        String getName,
        String getIconUrl,
        int getMemberCount
) {
    public static List<GuildsData> get(List<Long> guilds, JDA jda) {
        return guilds.stream().map(guildId -> {
            var guild = jda.getGuildById(guildId);
            if (guild != null) {
                return new GuildsData(
                        guild.getId(),
                        guild.getName(),
                        guild.getIconUrl(),
                        guild.getMemberCount()
                );
            } else {
                return null;
            }
        }).filter(Objects::nonNull).toList();
    }
}
