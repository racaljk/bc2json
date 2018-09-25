package serializer;

import b2j.Option;
import com.google.gson.*;
import dataobject.FieldObject;
import util.Readability;

import java.lang.reflect.Type;

public class FieldObjectSerializer implements JsonSerializer<FieldObject> {
    private Option option;

    public FieldObjectSerializer(Option option) {
        this.option = option;
    }

    @Override
    public JsonElement serialize(FieldObject f, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonFields = new JsonArray();
        String[][] fields = f.toStringMatrix();
        for (int i = 0; i < fields.length; i++) {
            JsonObject jsonField = new JsonObject();
            if (option.isMoreReadable()) {
                jsonField.addProperty("field",
                        Readability.getFieldAccessFlagString(Integer.valueOf(fields[i][2])) +
                                Readability.peelFieldDescriptor(fields[i][1]) + " " +
                                fields[i][0]);
            } else {
                jsonField.addProperty("field_name", fields[i][0]);
                jsonField.addProperty("field_type", fields[i][1]);
                jsonField.addProperty("access_flag", fields[i][2]);
            }

            jsonFields.add(jsonField);
        }
        return jsonFields;
    }
}
