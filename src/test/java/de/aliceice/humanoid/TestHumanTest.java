package de.aliceice.humanoid;

import de.aliceice.humanoid.sessions.InvalidUserSession;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    
    private final TestHuman subject = new TestHuman();
}