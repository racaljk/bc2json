package serialize;

import adt.u4;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class u4Serializer implements JsonSerializer<u4> {
    @Override
    public JsonElement serialize(u4 u4, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(u4.getValue());
    }
}
