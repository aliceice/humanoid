package de.aliceice.humanoid;

public abstract class Action {
    
    public String getName() {
        return this.name;
    }
    
    public abstract void run(Human human);
    
    protected Action(String name) {
        this.name = name;
    }
    
    private final String name;
}
