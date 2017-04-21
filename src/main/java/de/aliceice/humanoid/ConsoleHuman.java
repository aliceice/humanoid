package de.aliceice.humanoid;

import de.aliceice.humanoid.media.Media;
import de.aliceice.humanoid.sessions.UserSession;
import de.aliceice.humanoid.sessions.ValidUserSession;
import de.aliceice.paper.Console;
import de.aliceice.paper.ConsolePaper;
import de.aliceice.paper.Form;
import de.aliceice.paper.IOStreamConsole;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ConsoleHuman implements Human {
    
    @Override
    public void greet(String greeting) {
        this.console.println();
        this.console.println(greeting);
        this.console.println();
    }
    
    @Override
    public void goodbye(String farewell) {
        this.console.println(farewell);
        this.console.println();
    }
    
    @Override
    public void fyi(String info) {
        this.console.println(info);
        this.console.println();
    }
    
    @Override
    public void anErrorOccurred(String error) {
        this.console.println("An error occurred: " + error);
        this.console.println();
    }
    
    @Override
    public void hereYouGo(Response response) {
        this.console.println("-----------------------");
        this.console.println("| " + response.getName());
        this.console.println("|");
        response.printOn(new ResponseMedia(this.console));
        this.console.println("-----------------------");
    }
    
    @Override
    public void decideWhatToDo(Action... actions) {
        this.console.println("Please decide what to do:");
        this.console.println();
        
        IntStream.rangeClosed(1, actions.length)
                 .forEach(index -> this.console.printf("  %d. %s%n", index, actions[index - 1].getName()));
        this.console.println();
        
        String selection;
        do {
            selection = this.console.readLine("Selection: ");
        } while (selection.isEmpty() || isNotNumeric(selection) || notInRange(selection, actions.length));
        actions[Integer.parseInt(selection) - 1].run(this);
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
        ConsolePaper paper = new ConsolePaper(this.console);
        form.printOn(paper);
        
        do {
            paper.askForInput();
            paper.copyTo(form);
    
            this.console.println();
            
            if (form.isNotValid()) {
                form.markErrorsOn(paper);
                this.console.println();
            }
            
        } while (form.isNotValid());
        
        form.submit();
    }
    
    public ConsoleHuman() {
        this(new IOStreamConsole());
    }
    
    public ConsoleHuman(Console console) {
        this(console, new ValidUserSession());
    }
    
    public ConsoleHuman(Console console, UserSession userSession) {
        this.console = console;
        this.userSession = userSession;
    }
    
    private Boolean isNotNumeric(String selection) {
        return !selection.matches("\\d+");
    }
    
    private Boolean notInRange(String selection, Integer length) {
        Integer integer = Integer.valueOf(selection);
        return integer < 1 || integer > length;
    }
    
    private final Console     console;
    private final UserSession userSession;
    
    private static final class ResponseMedia implements Media<String> {
        
        @Override
        public void print(String name, String value) {
            this.console.printf("| %s: %s%n", name, value);
        }
        
        @Override
        public void print(String name, Response response) {
            print(name, response.getName());
        }
        
        @Override
        public void print(String name, Collection<Response> collection) {
            print(name, collection.stream()
                                  .map(Response::getName)
                                  .collect(Collectors.joining(", ",
                                                              "[ ",
                                                              " ]")));
        }
        
        @Override
        public String getContent() {
            return null;
        }
        
        private ResponseMedia(Console console) {
            this.console = console;
        }
        
        private final Console console;
    }
}
