package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {
    @Override
    public String getRule(final int ruleNumber) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Overrirde
    public void writeName(final String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean writeDetails(final String details) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getDeathCause(final String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getDeathDetails(final String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isNameWritten(final String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}