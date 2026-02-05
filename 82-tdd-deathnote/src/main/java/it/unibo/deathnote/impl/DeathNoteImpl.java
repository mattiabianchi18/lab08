package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class DeathNoteImpl implements DeathNote {

    private final Set<String> writtenNames = new HashSet<>();
    private final Map<String, String> deathCauses = new HashMap<>();
    private String lastWrittenName;
    private long lastTimeWritten;
    private final Map<String, String> deathDetails = new HashMap<>();
    private long lastCauseWriteTime;
    private static final long TIME_LIMIT = 6040; // 6.04 seconds

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Invalid rule number: " + ruleNumber);
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (!name.isBlank()) {
            writtenNames.add(name);
            lastWrittenName = name;
            lastTimeWritten = System.currentTimeMillis();
        }
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("Cause cannot be null");
        }
        if (this.lastWrittenName == null) {
            throw new IllegalStateException("No name has been written yet");
        }

        final long now = System.currentTimeMillis();
        if (now - this.lastTimeWritten > TIME_LIMIT) {
            return false;
        }

        if(this.deathCauses.containsKey(this.lastWrittenName)) {
            return false;
        }

        if (now - this.lastTimeWritten <= TIME_LIMIT) {
            this.deathCauses.put(this.lastWrittenName, cause);
            this.lastCauseWriteTime = now;
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("Details cannot be null");
        }
        if (this.lastWrittenName == null) {
            throw new IllegalStateException("No name has been written yet");
        }

        final long referenceTime = this.lastCauseWriteTime != 0 ? this.lastCauseWriteTime : this.lastTimeWritten;

        final long now = System.currentTimeMillis();
        if (now - referenceTime <= TIME_LIMIT) {
            this.deathDetails.put(this.lastWrittenName, details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(final String name) {
        if (!this.writtenNames.contains(name)) {
            throw new IllegalArgumentException("Name not written");
        }
        return this.deathCauses.getOrDefault(name, "heart attack");
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!this.writtenNames.contains(name)) {
            throw new IllegalArgumentException("Name not written");
        }
        return this.deathDetails.getOrDefault(name, "");
    }

    @Override
    public boolean isNameWritten(final String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return writtenNames.contains(name);
    }
}