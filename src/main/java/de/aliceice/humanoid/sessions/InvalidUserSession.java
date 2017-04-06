package de.aliceice.humanoid.sessions;

public final class InvalidUserSession extends RuntimeException implements UserSession {
    
    @Override
    public void ifValidDo(Runnable action) {
        throw this;
    }
    
}
