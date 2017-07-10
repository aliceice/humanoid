package de.aliceice.humanoid;

import de.aliceice.humanoid.media.ConsoleMedia;
import de.aliceice.humanoid.media.Media;
import java.io.PrintStream;

public interface Response {
    
    String getName();
    
    void printOn(Media<?> media);
    
    default void printOn(PrintStream stream) {
        ConsoleMedia media = new ConsoleMedia();
        printOn(media);
    
        stream.println("-----------------------");
        stream.println("| " + getName());
        stream.println("|");
        stream.println(media.getContent());
        stream.println("-----------------------");
    }
}
