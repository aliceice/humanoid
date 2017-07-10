package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public final class JsonMedia implements Media<JsonValue> {
    
    @Override
    public void print(String name, Object value) {
        this.json.add(name, value.toString());
    }
    
    @Override
    public void print(String name, Response response) {
        JsonMedia media = new JsonMedia();
        response.printOn(media);
        this.json.add(name, media.getContent());
    }
    
    @Override
    public void print(String name, Collection<Response> collection) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        collection.forEach(response -> {
            JsonMedia entry = new JsonMedia();
            response.printOn(entry);
            arrayBuilder.add(entry.getContent());
        });
        this.json.add(name, arrayBuilder.build());
    }
    
    @Override
    public JsonValue getContent() {
        return this.json.build();
    }
    
    @Override
    public void print(String name, Integer value) {
        this.json.add(name, value);
    }
    
    @Override
    public void print(String name, Float value) {
        this.json.add(name, value);
    }
    
    @Override
    public void print(String name, Double value) {
        this.json.add(name, value);
    }
    
    @Override
    public void print(String name, Long value) {
        this.json.add(name, value);
    }
    
    private final JsonObjectBuilder json = Json.createObjectBuilder();
}
