package de.aliceice.humanoid.sessions;

import java.util.function.Supplier;

public interface UserSession {
    
    void ifValidDo(Runnable action);
    
    <T> T ifValidGet(Supplier<T> supplier);
}
