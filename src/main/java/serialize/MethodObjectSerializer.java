package serialize;

import com.google.gson.*;
import dataobject.MethodObject;

import java.lang.reflect.Type;

public class MethodObjectSerializer implements JsonSerializer<MethodObject> {
    @Override
    public JsonElement serialize(MethodObject m, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonMethods = new JsonArray();
        String[][] methods = m.toStringMatrix();
        for (String[] method : methods) {
            JsonObject jsonMethod = new JsonObject();
            jsonMethod.addProperty("method_name", method[0]);
            jsonMethod.addProperty("method_type", method[1]);
            jsonMethod.addProperty("method_flag", method[2]);
            jsonMethod.addProperty("method_opcode", method[3]);
            jsonMethods.add(jsonMethod);
        }
        return jsonMethods;
    }
}
