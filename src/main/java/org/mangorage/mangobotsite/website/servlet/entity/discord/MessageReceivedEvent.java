package org.mangorage.mangobotsite.website.servlet.entity.discord;

import net.dv8tion.jda.api.entities.Message;

public record MessageReceivedEvent(String guildId, String channelId, String messageId, String authorId, String message) {
    public static MessageReceivedEvent of(Message message) {
        return new MessageReceivedEvent(
                message.getGuildId(),
                message.getChannelId(),
                message.getId(),
                message.getAuthor().getId(),
                message.getContentRaw()
        );
    }
}
