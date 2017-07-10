package de.aliceice.humanoid;

import de.aliceice.humanoid.media.Media;
import de.aliceice.humanoid.sessions.InvalidUserSession;
import de.aliceice.paper.Field;
import de.aliceice.paper.Fields;
import de.aliceice.paper.Form;
import de.aliceice.paper.MandatoryRule;
import de.aliceice.paper.TestConsole;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(EnglishLocaleExtension.class)
public final class ConsoleHumanTest {
    
    @Test
    public void greet() throws Exception {
        this.subject.greet("Hello, %s!", "there");
        this.console.hasOutput("%nHello, there!%n%n");
    }
    
    @Test
    public void goodbye() throws Exception {
        this.subject.goodbye("Fare%s", "well");
        this.console.hasOutput("Farewell%n%n");
    }
    
    @Test
    public void fyi() throws Exception {
        this.subject.fyi("Infor%s", "mation");
        this.console.hasOutput("Information%n%n");
    }
    
    @Test
    public void anErrorOccurred() throws Exception {
        this.subject.anErrorOccurred("Err%s", "or");
        this.console.hasOutput("An error occurred: Error%n%n");
    }
    
    @Test
    public void hereYouGo() throws Exception {
        this.subject.hereYouGo(new Response() {
            @Override
            public String getName() {
                return "Response";
            }
            
            @Override
            public void printOn(Media<?> media) {
                media.print("Field 1", "Value 1");
                media.print("Response", new StringResponse("Name", "Value"));
                media.print("Response Collection",
                            Arrays.asList(new StringResponse("Name 1", "Value"),
                                          new StringResponse("Name 2", "Value")));
            }
            
            @Override
            public void printOn(PrintStream stream) {
            
            }
        });
        
        this.console.hasOutput("-----------------------%n" +
                               "| Response%n" +
                               "|%n" +
                               "| Field 1: Value 1%n" +
                               "| Response: Name%n" +
                               "| Response Collection: [ Name 1, Name 2 ]%n" +
                               "-----------------------%n");
    }
    
    @Test
    public void decideWhatToDo() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        this.console.useAsInput("", "ABC", "0", "3", "1");
        
        this.subject.decideWhatToDo(new InlineAction("Count up", counter::incrementAndGet),
                                    new InlineAction("Do nothing", () -> {}));
        
        this.console.hasOutput("Please decide what to do:%n" +
                               "%n" +
                               "  1. Count up%n" +
                               "  2. Do nothing%n" +
                               "%n" +
                               "Selection: %n" +
                               "Selection: ABC%n" +
                               "Selection: 0%n" +
                               "Selection: 3%n" +
                               "Selection: 1%n");
        assertEquals(1, counter.get());
    }
    
    @Test
    public void authenticated() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        this.subject.authenticated((Runnable) counter::incrementAndGet);
        assertEquals(1, counter.get());
        assertEquals((Integer) 2, this.subject.authenticated(counter::incrementAndGet));
    }
    
    @Test
    public void fillOutAndSubmit() throws Exception {
        this.console.useAsInput("", "", "Value 1", "Value 2");
        Form form = new Form("Test",
                             new Fields(new Field("Field 1", new MandatoryRule()),
                                        new Field("Field 2", new MandatoryRule())));
        form.onSubmit(fields -> this.console.println(fields.toString()));
        
        this.subject.fillOutAndSubmit(form);
        
        this.console.hasOutput("Test%n" +
                               "%n" +
                               "%n" +
                               "Field 1: %n" +
                               "Field 2: %n" +
                               "%n" +
                               "There are errors on the form:%n" +
                               "  - Field 1: Mandatory%n" +
                               "  - Field 2: Mandatory%n" +
                               "%n" +
                               "Field 1: Value 1%n" +
                               "Field 2: Value 2%n" +
                               "%n" +
                               "{Field 1=Value 1, Field 2=Value 2}%n");
    }
    
    @Test
    public void defaultConstructorUsesIOStreamConsole() throws Exception {
        PrintStream old = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        ConsoleHuman human = new ConsoleHuman();
        human.fyi("Information");
        
        assertEquals(String.format("Information%n%n"), out.toString());
        
        System.setOut(old);
    }
    
    @Test
    public void canConstructUserSessionFromFunction() throws Exception {
        ConsoleHuman subject = new ConsoleHuman(new InvalidUserSession());
        assertThrows(InvalidUserSession.class,
                     () -> subject.authenticated(() -> {}));
    }
    
    private final TestConsole console = new TestConsole();
    private final Human       subject = new ConsoleHuman(this.console);
}