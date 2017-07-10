package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.Collection;

public interface Media<T> {
    
    void print(String name, Object value);
    
    void print(String name, Response response);
    
    void print(String name, Collection<Response> collection);
    
    T getContent();
    
    default void print(String name, String value) {
        print(name, (Object) value);
    }
    
    default void print(String name, Integer value) {
        print(name, (Object) value);
    }
    
    default void print(String name, Float value) {
        print(name, (Object) value);
    }
    
    default void print(String name, Double value) {
        print(name, (Object) value);
    }
    
    default void print(String name, Long value) {
        print(name, (Object) value);
    }
}
