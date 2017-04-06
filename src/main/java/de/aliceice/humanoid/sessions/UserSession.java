package de.aliceice.humanoid.sessions;

public interface UserSession {
    
    void ifValidDo(Runnable action);
    
}
