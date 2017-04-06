package de.aliceice.humanoid;

import java.util.function.Supplier;

public interface Human {
    
    void greet(String greeting);
    
    void goodbye(String farewell);
    
    void fyi(String info);
    
    void anErrorOccurred(String error);
    
    void decideWhatToDo(Action... actions);
    
    void authenticated(Runnable action);
    
    <T> T authenticated(Supplier<T> supplier);
}
