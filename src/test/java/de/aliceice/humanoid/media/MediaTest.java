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
        this.subject.print("Long", 0L);
        this.subject.print("Float", .0F);
        this.subject.print("Double", .0D);
        this.subject.print("String", "Value");
        
        assertEquals(0, this.printedValues.get("Integer"));
        assertEquals(0L, this.printedValues.get("Long"));
        assertEquals(.0F, this.printedValues.get("Float"));
        assertEquals(.0D, this.printedValues.get("Double"));
    }
    
    private final Map<String, Object> printedValues = new HashMap<>();
    private final Media<String>       subject       = new Media<String>() {
        
        @Override
        public void print(String name, Object value) {
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