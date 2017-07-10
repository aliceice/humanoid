package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ConsoleMedia implements Media<String> {
    
    @Override
    public void print(String name, Object value) {
        this.lines.add(String.format("%s %s: %s", this.prefix, name, value));
    }
    
    @Override
    public void print(String name, Response response) {
        print(name, response.getName());
    }
    
    @Override
    public void print(String name, Collection<Response> collection) {
        print(name, collection.stream()
                              .map(Response::getName)
                              .collect(Collectors.joining(", ", "[ ", " ]")));
    }
    
    @Override
    public String getContent() {
        return this.lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
    
    public ConsoleMedia() {
        this("|");
    }
    
    public ConsoleMedia(String prefix) {
        this.prefix = prefix;
        this.lines = new ArrayList<>();
    }
    
    private final String       prefix;
    private final List<String> lines;
}
