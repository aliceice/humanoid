package de.aliceice.humanoid;

import de.aliceice.humanoid.sessions.InvalidUserSession;
import de.aliceice.paper.Field;
import de.aliceice.paper.Fields;
import de.aliceice.paper.Form;
import de.aliceice.paper.MandatoryRule;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TestHumanTest {
    
    @Test
    public void greet() throws Exception {
        this.subject.greet("Hi there!");
        this.subject.wasGreeted();
    }
    
    @Test
    public void goodbye() throws Exception {
        this.subject.goodbye("See you!");
        this.subject.wasSeenOff();
    }
    
    @Test
    public void fyi() throws Exception {
        String info = "Something that might interest you";
        this.subject.fyi(info);
        this.subject.hasInfo(info);
    }
    
    @Test
    public void anErrorOccurred() throws Exception {
        String errorMessage = "Detailed error message";
        this.subject.anErrorOccurred(errorMessage);
        this.subject.hasError(errorMessage);
    }
    
    @Test
    public void hereYouGo() throws Exception {
        this.subject.hereYouGo("endpoint", "http://www.google.de");
        this.subject.hasResponse("endpoint", "http://www.google.de");
        
        this.subject.hereYouGo(new StringResponse("endpoint2", "http://www.google.de"));
        this.subject.hasResponse("endpoint2", "http://www.google.de");
    }
    
    @Test
    public void decideWhatToDo() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        Action[] actions = {
            new InlineAction("Nothing", human -> {}),
            new InlineAction("Increment counter", counter::incrementAndGet)
        };
        
        this.subject.ifAskedDo("Increment counter", "Nothing")
                    .decideWhatToDo(actions);
        
        assertEquals(1, counter.get());
        
        this.subject.decideWhatToDo(actions);
        
        assertEquals(1, counter.get());
    }
    
    @Test
    public void authenticated() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        
        this.subject.authenticated((Runnable) counter::incrementAndGet);
        assertEquals(1, counter.get());
        assertEquals(Integer.valueOf(2),
                     this.subject.authenticated(counter::incrementAndGet));
        
        this.subject.logOut();
        assertThrows(InvalidUserSession.class,
                     () -> this.subject.authenticated((Runnable) counter::incrementAndGet));
        assertThrows(InvalidUserSession.class,
                     () -> this.subject.authenticated(counter::incrementAndGet));
    }
    
    @Test
    public void fillOutAndSubmit() throws Exception {
        AtomicBoolean wasSubmitted = new AtomicBoolean();
        Form form = new Form("Test Form",
                             new Fields(new Field("Field 1", new MandatoryRule()),
                                        new Field("Field 2", new MandatoryRule())));
        form.onSubmit(fields -> {
            wasSubmitted.set(true);
            assertEquals("Value 1", fields.get("Field 1"));
            assertEquals("Value 2", fields.get("Field 2"));
        });
        
        this.subject.takeNotes("Test Form",
                               "Field 1: Value 1",
                               "Field 2: Value 2");
        this.subject.fillOutAndSubmit(form);
        
        assertTrue(wasSubmitted.get());
        
        Throwable exception = assertThrows(RuntimeException.class,
                                           () -> this.subject.fillOutAndSubmit(new Form("Unknown", new Fields())));
        assertEquals("I do not have notes for Form: Unknown", exception.getMessage());
    }
    
    private final TestHuman subject = new TestHuman();
}