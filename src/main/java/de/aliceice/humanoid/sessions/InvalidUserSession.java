package de.aliceice.humanoid.sessions;

import java.util.function.Supplier;

public final class InvalidUserSession extends RuntimeException implements UserSession {
    
    @Override
    public void ifValidDo(Runnable action) {
        throw this;
    }
    
    @Override
    public <T> T ifValidGet(Supplier<T> supplier) {
        throw this;
    }
    
}
