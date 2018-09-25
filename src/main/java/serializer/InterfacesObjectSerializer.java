package serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataobject.InterfacesObject;

import java.lang.reflect.Type;

public class InterfacesObjectSerializer implements JsonSerializer<InterfacesObject> {
    @Override
    public JsonElement serialize(InterfacesObject interfacesObject, Type type, JsonSerializationContext jsonSerializationContext) {
        String[] interfaces = interfacesObject.toStringMatrix();
        JsonArray jsonInterfaces = new JsonArray();
        for (int i = 0; i < interfaces.length; i++) {
            jsonInterfaces.add(interfaces[i]);
        }
        return jsonInterfaces;
    }
}
