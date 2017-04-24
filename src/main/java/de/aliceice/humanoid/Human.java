package de.aliceice.humanoid;

import de.aliceice.paper.Form;
import java.util.function.Supplier;

public interface Human {
    
    default void greet(String template, Object... args) {
        greet(String.format(template, args));
    }
    
    void greet(String greeting);
    
    default void goodbye(String template, Object... args) {
        goodbye(String.format(template, args));
    }
    
    void goodbye(String farewell);
    
    default void fyi(String template, Object... args) {
        fyi(String.format(template, args));
    }
    
    void fyi(String info);
    
    default void anErrorOccurred(String template, Object... args) {
        anErrorOccurred(String.format(template, args));
    }
    
    void anErrorOccurred(String error);
    
    void hereYouGo(Response response);
    
    default void hereYouGo(String name, String response) {
        hereYouGo(new StringResponse(name, response));
    }
    
    void decideWhatToDo(Action... actions);
    
    void authenticated(Runnable action);
    
    <T> T authenticated(Supplier<T> supplier);
    
    void fillOutAndSubmit(Form form);
}
