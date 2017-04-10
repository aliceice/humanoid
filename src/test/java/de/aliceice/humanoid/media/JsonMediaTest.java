package de.aliceice.humanoid.media;

import de.aliceice.humanoid.StringResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JsonMediaTest {
    
    @Test
    public void printString() throws Exception {
        this.subject.print("Field", "Value");
        assertEquals("{\"Field\":\"Value\"}", this.subject.getContent().toString());
    }
    
    @Test
    public void printInteger() throws Exception {
        this.subject.print("Field", 1);
        assertEquals("{\"Field\":1}", this.subject.getContent().toString());
    }
    
    @Test
    public void printFloat() throws Exception {
        this.subject.print("Field", 1.f);
        assertEquals("{\"Field\":1.0}", this.subject.getContent().toString());
    }
    
    @Test
    public void printDouble() throws Exception {
        this.subject.print("Field", 1.d);
        assertEquals("{\"Field\":1.0}", this.subject.getContent().toString());
    }
    
    @Test
    public void printResponse() throws Exception {
        this.subject.print("Field", new StringResponse("Embedded Field", "Value"));
        assertEquals("{\"Field\":{\"Embedded Field\":\"Value\"}}", this.subject.getContent().toString());
    }
    
    @Test
    public void printCollectionOfResponses() throws Exception {
        this.subject.print("Field", Arrays.asList(new StringResponse("Embedded Field", "Value"),
                                                  new StringResponse("Embedded Field", "Value")));
        assertEquals("{\"Field\":[{\"Embedded Field\":\"Value\"},{\"Embedded Field\":\"Value\"}]}",
                     this.subject.getContent().toString());
    }
    
    private final JsonMedia subject = new JsonMedia();
}