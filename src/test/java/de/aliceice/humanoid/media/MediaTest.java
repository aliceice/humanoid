package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MediaTest {
    
    @Test
    public void defaultMethods() throws Exception {
        this.subject.print("Integer", 0);
        this.subject.print("Float", .0F);
        this.subject.print("Double", .0D);
        
        assertEquals("0", this.printedValues.get("Integer"));
        assertEquals("0.0", this.printedValues.get("Float"));
        assertEquals("0.0", this.printedValues.get("Double"));
    }
    
    private final Map<String, String> printedValues = new HashMap<>();
    private final Media<String>       subject       = new Media<String>() {
        
        @Override
        public void print(String name, String value) {
            printedValues.put(name, value);
        }
        
        @Override
        public void print(String name, Response response) {
        
        }
        
        @Override
        public void print(String name, Collection collection) {
        
        }
        
        @Override
        public String getContent() {
            return printedValues.toString();
        }
    };
}