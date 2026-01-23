package org.mangorage.mangobotsite.website.servlet.data;

import org.mangorage.mangobotplugin.commands.trick.Trick;

import java.util.Objects;

public final class TrickData {
    private final Trick trick;

    public TrickData(Trick trick) {
        this.trick = Objects.requireNonNull(trick, "trick");
    }

    public String getId() {
        return Objects.requireNonNullElse(trick.getTrickID(), "");
    }

    public String getGuildId() {
        return trick.getGuildID() + "";
    }

    public String getName() {
        return Objects.requireNonNullElse(trick.getTrickID(), "Unknown Trick");
    }

    public String getType() {
        return trick.getType() == null ? "N/A" : trick.getType().name();
    }

    public String getContent() {
        return Objects.requireNonNullElse(trick.getContent(), "No content");
    }

    public boolean isLocked() {
        return trick.isLocked();
    }

    public boolean isSuppressEmbeds() {
        return trick.isSuppressed();
    }

    public String getOwnerName() {
        return "Not Implemented";
    }

    public String getOwnerId() {
        return trick.getOwnerID() + "";
    }

    public long getUsageCount() {
        return Math.max(0, trick.getTimesUsed());
    }

    public String getCreatedDate() {
        return trick.getCreated() + "";
    }

    public String getLastEditedDate() {
        return trick.getLastEdited() + "";
    }

    public String getLastModifiedByName() {
        return trick.getLastUserEdited() + "";
    }
}
