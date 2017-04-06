package de.aliceice.humanoid.sessions;

import java.util.function.Supplier;

public final class ValidUserSession implements UserSession {
    
    @Override
    public void ifValidDo(Runnable action) {
        action.run();
    }
    
    @Override
    public <T> T ifValidGet(Supplier<T> supplier) {
        return supplier.get();
    }
    
}
