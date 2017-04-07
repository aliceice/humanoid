package de.aliceice.humanoid;

import de.aliceice.humanoid.media.Media;
import java.io.PrintStream;

public final class StringResponse implements Response {
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void printOn(Media media) {
        media.print(this.name, this.value);
    }
    
    @Override
    public void printOn(PrintStream stream) {
        stream.print(this.value);
    }
    
    public StringResponse(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    private final String name;
    private final String value;
}
