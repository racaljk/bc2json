package serialize;

import com.google.gson.*;
import dataobject.InterfacesObject;

import java.lang.reflect.Type;

public class InterfacesObjectSerializer implements JsonSerializer<InterfacesObject> {
    @Override
    public JsonElement serialize(InterfacesObject interfacesObject, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        String[] interfaces = interfacesObject.getAllInterfaces();
        result.addProperty("interface_num", interfaces.length);
        JsonArray jsonInterfaces = new JsonArray();
        for (int i = 0; i < interfaces.length; i++) {
            jsonInterfaces.add(interfaces[i]);
        }
        result.add("interfaces", jsonInterfaces);
        return result;
    }
}
