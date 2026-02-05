package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestDeathNote {

    @Test
    void testInvalidRuleNumbersThrowsException() {
        DeathNote dn = new DeathNoteImpl();

        final Exception ex1 = assertThrows(IllegalArgumentException.class, () -> dn.getRule(0));
        assertNotNull(ex1.getMessage());
        assertFalse(ex1.getMessage().isBlank());

        final Exception ex2 = assertThrows(IllegalArgumentException.class, () -> dn.getRule(-1));
        assertNotNull(ex2.getMessage());
        assertFalse(ex2.getMessage().isBlank());
    }

    @Test
    void testAllRulesAreNonNullAndNonBlank() {
        DeathNote dn = new DeathNoteImpl();

        for (int i = 1; i <= DeathNote.RULES.size(); i++) {
            final String rule = dn.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testWriteNameAndNameIsWritten() {
        DeathNote dn = new DeathNoteImpl();

        assertFalse(dn.isNameWritten("Light Yagami"));

        dn.writeName("Light Yagami");
        assertTrue(dn.isNameWritten("Light Yagami"));
        assertFalse(dn.isNameWritten("L"));
        assertFalse(dn.isNameWritten(""));
    }

    @Test
    void testDeathCauseTiming() {
        DeathNote dn = new DeathNoteImpl();

        assertThrows(IllegalStateException.class, () -> dn.writeDeathCause("Heart Attack"));

        dn.writeName("L");

        assertEquals("heart attack", dn.getDeathCause("L"));

        dn.writeName("Near");
        final boolean written = dn.writeDeathCause("karting accident");
        assertTrue(written);
        assertEquals("karting accident", dn.getDeathCause("Near"));

        final boolean changed = dn.writeDeathCause("poison");
        assertFalse(changed);
        assertEquals("karting accident", dn.getDeathCause("Near"));
    }

    @Test
    void testDeathDetailsTiming() throws InterruptedException {
        final DeathNote dn = new DeathNoteImpl();

        assertThrows(IllegalStateException.class, () -> dn.writeDetails("details"));

        dn.writeName("Misa");
        assertEquals("", dn.getDeathDetails("Misa"));

        final boolean written = dn.writeDetails("run far too long");
        assertTrue(written);
        assertEquals("run far too long", dn.getDeathDetails("Misa"));

        dn.writeName("Ryuk");
        Thread.sleep(6100);

        final boolean changed = dn.writeDetails("new details");
        assertFalse(changed);
    }
}