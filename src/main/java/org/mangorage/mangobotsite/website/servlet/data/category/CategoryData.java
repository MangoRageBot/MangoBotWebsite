package org.mangorage.mangobotsite.website.servlet.data.category;

import net.dv8tion.jda.api.JDA;
import org.mangorage.mangobotplugin.commands.trick.Trick;

import java.util.List;

public record CategoryData(String name, List<CategoryItemData> info) {
    public static List<CategoryData> of(Trick trick, JDA jda) {
        final var guild = jda.getGuildById(trick.getGuildID());
        return List.of(
                new CategoryData(
                        "DETAILS",
                        List.of(
                                new CategoryItemData(
                                        "Guild",
                                        guild == null ? "N/A" : guild.getName(),
                                        false
                                ),
                                new CategoryItemData(
                                        "Guild Id",
                                        trick.getGuildID() + "",
                                        false
                                ),
                                new CategoryItemData(
                                        "Guild ID",
                                        trick.getGuildID() + "",
                                        false
                                ),
                                new CategoryItemData(
                                        "Owner ID",
                                        trick.getOwnerID() + "",
                                        false
                                )
                        )
                ),
                new CategoryData(
                        "STATISTICS",
                        List.of(
                                new CategoryItemData(
                                        "Times Used",
                                        trick.getTimesUsed() + "",
                                        false
                                ),
                                new CategoryItemData(
                                        "Created",
                                        trick.getCreated() + "",
                                        false
                                ),
                                new CategoryItemData(
                                        "Last Edited",
                                        trick.getLastEdited() + "",
                                        false
                                ),
                                new CategoryItemData(
                                        "Last Modified By",
                                        trick.getLastUserEdited() + "",
                                        false
                                )
                        )
                ),
                new CategoryData(
                        "SETTINGS",
                        List.of(
                                new CategoryItemData(
                                        "Locked",
                                        trick.isLocked() ? "Yes" : "No",
                                        false
                                ),
                                new CategoryItemData(
                                        "Suppress Embeds",
                                        trick.isSuppressed() ? "Yes" : "No",
                                        false
                                )
                        )
                )
        );
    }
}
