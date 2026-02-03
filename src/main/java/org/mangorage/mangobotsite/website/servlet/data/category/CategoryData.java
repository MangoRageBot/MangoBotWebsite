package org.mangorage.mangobotsite.website.servlet.data.category;

import net.dv8tion.jda.api.JDA;
import org.mangorage.mangobotplugin.commands.trick.Trick;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record CategoryData(String name, List<CategoryItemData> info) {
    static String time(long epochMilli) {
        // Convert milliseconds to ZonedDateTime in Los Angeles timezone
        ZonedDateTime zdt = Instant.ofEpochMilli(epochMilli)
                .atZone(ZoneId.of("America/Los_Angeles"));

        // Create formatter for: Jan 1, 1900 @ 12:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy @ HH:mm (zzz)");

        return zdt.format(formatter);
    }

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
                                        time(trick.getCreated()),
                                        false
                                ),
                                new CategoryItemData(
                                        "Last Edited",
                                        time(trick.getLastEdited()),
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
