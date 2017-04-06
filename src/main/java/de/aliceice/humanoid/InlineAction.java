package de.aliceice.humanoid;

import java.util.function.Consumer;

public final class InlineAction extends Action {
    
    public InlineAction(String name, Runnable action) {
        this(name, __ -> action.run());
    }
    
    public InlineAction(String name, Consumer<Human> action) {
        super(name);
        this.action = action;
    }
    
    @Override
    protected void run(Human human) {
        this.action.accept(human);
    }
    
    private final Consumer<Human> action;
}
