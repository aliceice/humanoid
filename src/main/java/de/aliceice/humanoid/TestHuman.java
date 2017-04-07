package de.aliceice.humanoid;

import de.aliceice.humanoid.sessions.InvalidUserSession;
import de.aliceice.humanoid.sessions.UserSession;
import de.aliceice.humanoid.sessions.ValidUserSession;
import de.aliceice.paper.Form;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void hereYouGo(Response response) {
        this.receivedResponses.put(response.getName(), response);
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
    public <T> T authenticated(Supplier<T> supplier) {
        return this.userSession.ifValidGet(supplier);
    }
    
    @Override
    public void fillOutAndSubmit(Form form) {
        Optional.ofNullable(this.notesByTitle.get(form.getName()))
                .orElseThrow(() -> new RuntimeException("I do not have notes for Form: " + form.getName()))
                .forEach(note -> {
                    String[] noteElements = note.split(": ");
                    form.write(noteElements[0], noteElements[1]);
                });
        form.submit();
    }
    
    @Override
    public String toString() {
        return String.format("-----------------------%n" +
                             "|%n" +
                             "| Received Info: %s%n" +
                             "| Received Errors: %s%n" +
                             "| Received Responses: %s%n" +
                             "|%n" +
                             "-----------------------%n",
                             this.receivedInfo,
                             this.receivedErrors,
                             this.receivedResponses);
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
    
    public TestHuman hasResponse(String name, String response) {
        assertTrue(this.receivedResponses.containsKey(name),
                   String.format("Did not receive response '%s'%n%s", name, this));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.receivedResponses.get(name).printOn(new PrintStream(out));
        assertEquals(response, out.toString());
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
    
    public void takeNotes(String name, String... notes) {
        this.notesByTitle.put(name, Arrays.asList(notes));
    }
    
    private Boolean                   greeted           = false;
    private Boolean                   seenOff           = false;
    private List<String>              receivedInfo      = new ArrayList<>();
    private List<String>              receivedErrors    = new ArrayList<>();
    private LinkedList<String>        actionsToTake     = new LinkedList<>();
    private UserSession               userSession       = new ValidUserSession();
    private Map<String, Response>     receivedResponses = new HashMap<>();
    private Map<String, List<String>> notesByTitle      = new HashMap<>();
}
