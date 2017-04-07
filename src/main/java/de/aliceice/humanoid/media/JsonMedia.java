package de.aliceice.humanoid.media;

import de.aliceice.humanoid.Response;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public final class JsonMedia implements Media {
    
    @Override
    public void print(String name, String value) {
        this.json.add(name, value);
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
    public void print(String name, Response response) {
        JsonMedia value = new JsonMedia();
        response.printOn(value);
        this.json.add(name, value.getJson());
    }
    
    @Override
    public void print(String name, Collection<Response> collection) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        collection.forEach(response -> {
            JsonMedia entry = new JsonMedia();
            response.printOn(entry);
            arrayBuilder.add(entry.getJson());
        });
        this.json.add(name, arrayBuilder.build());
    }
    
    public JsonValue getJson() {
        return this.json.build();
    }
    
    private final JsonObjectBuilder json = Json.createObjectBuilder();
}
