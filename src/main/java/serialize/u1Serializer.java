package serialize;

import adt.u1;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class u1Serializer implements JsonSerializer<u1> {
    @Override
    public JsonElement serialize(u1 u1, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(u1.getValue());
    }
}
