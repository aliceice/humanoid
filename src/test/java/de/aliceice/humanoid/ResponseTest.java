package de.aliceice.humanoid;

import de.aliceice.humanoid.media.Media;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ResponseTest {
    
    @Test
    public void defaultPrintOnStream() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.subject.printOn(new PrintStream(out));
        assertEquals(String.format("-----------------------%n" +
                                   "| Response%n" +
                                   "|%n" +
                                   "| Field: Value%n" +
                                   "-----------------------%n"),
                     out.toString());
    }

    private final Response subject = new Response() {
        @Override
        public String getName() {
            return "Response";
        }
    
        @Override
        public void printOn(Media<?> media) {
            media.print("Field", "Value");
        }
    };
}