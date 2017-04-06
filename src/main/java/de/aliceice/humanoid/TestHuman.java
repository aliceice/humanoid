package de.aliceice.humanoid;

import de.aliceice.humanoid.sessions.InvalidUserSession;
import de.aliceice.humanoid.sessions.UserSession;
import de.aliceice.humanoid.sessions.ValidUserSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TestHuman implements Human {
    
    @Override
    public void greet(String greeting) {
        this.greeted = true;
    }
    
    @Override
    public void goodbye(String farewell) {
        this.seenOff = true;
    }
    
    @Override
    public void fyi(String info) {
        this.receivedInfo.add(info);
    }
    
    @Override
    public void anErrorOccurred(String error) {
        this.receivedErrors.add(error);
    }
    
    @Override
    public void decideWhatToDo(Action... actions) {
        Optional.ofNullable(this.actionsToTake.pollFirst())
                .ifPresent(actionToTake -> {
                    Stream.of(actions)
                          .filter(action -> actionToTake.equals(action.getName()))
                          .findFirst()
                          .ifPresent(action -> action.run(this));
                });
    }
    
    @Override
    public void authenticated(Runnable action) {
        this.userSession.ifValidDo(action);
    }
    
    @Override
    public String toString() {
        return String.format("-----------------------%n" +
                             "|%n" +
                             "| Received Info: %s%n" +
                             "| Received Errors: %s%n" +
                             "|%n" +
                             "-----------------------%n",
                             this.receivedInfo,
                             this.receivedErrors);
    }
    
    public TestHuman wasGreeted() {
        assertTrue(this.greeted, "Human was not greeted.");
        return this;
    }
    
    public TestHuman wasSeenOff() {
        assertTrue(this.seenOff);
        return this;
    }
    
    public TestHuman hasInfo(String info) {
        assertTrue(this.receivedInfo.contains(info),
                   String.format("Did not receive info '%s'%n%s", info, this));
        return this;
    }
    
    public TestHuman hasError(String error) {
        assertTrue(this.receivedErrors.contains(error),
                   String.format("Did not receive error '%s'%n%s", error, this));
        return this;
    }
    
    public TestHuman ifAskedDo(String... actionsToTake) {
        this.actionsToTake.addAll(Arrays.asList(actionsToTake));
        return this;
    }
    
    public TestHuman logOut() {
        this.userSession = new InvalidUserSession();
        return this;
    }
    
    private Boolean            greeted        = false;
    private Boolean            seenOff        = false;
    private List<String>       receivedInfo   = new ArrayList<>();
    private List<String>       receivedErrors = new ArrayList<>();
    private LinkedList<String> actionsToTake  = new LinkedList<>();
    private UserSession        userSession    = new ValidUserSession();
}
