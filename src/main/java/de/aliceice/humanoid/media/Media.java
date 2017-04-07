package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.Collection;

public interface Media {
    
    void print(String name, String value);
    
    void print(String name, Integer value);
    
    void print(String name, Float value);
    
    void print(String name, Double value);
    
    void print(String name, Response collection);
    
    void print(String name, Collection<Response> collection);
}
