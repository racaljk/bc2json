package serialize;

import adt.u2;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class u2Serializer implements JsonSerializer<u2> {
    @Override
    public JsonElement serialize(u2 u2, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(u2.getValue());
    }
}
