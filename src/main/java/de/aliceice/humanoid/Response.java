package de.aliceice.humanoid;

import de.aliceice.humanoid.media.Media;
import java.io.PrintStream;

public interface Response {
    
    String getName();
    
    void printOn(Media media);
    
    void printOn(PrintStream stream);
}
