package de.aliceice.humanoid.sessions;

public final class ValidUserSession implements UserSession {
    
    @Override
    public void ifValidDo(Runnable action) {
        action.run();
    }
    
}
