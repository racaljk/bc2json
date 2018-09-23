package serialize;

import com.google.gson.*;
import dataobject.FieldObject;

import java.lang.reflect.Type;

public class FieldObjectSerializer implements JsonSerializer<FieldObject> {
    private boolean moreReadable;

    public FieldObjectSerializer(boolean moreReadable) {
        this.moreReadable = moreReadable;
    }

    private static String getAccessFlagString(int acc) {
        StringBuilder sb = new StringBuilder();
        if ((acc & 0x0001) != 0) {
            sb.append("public ");
        }
        if ((acc & 0x0002) != 0) {
            sb.append("private ");
        }
        if ((acc & 0x0004) != 0) {
            sb.append("protected ");
        }
        if ((acc & 0x0008) != 0) {
            sb.append("static ");
        }
        if ((acc & 0x0010) != 0) {
            sb.append("final ");
        }
        if ((acc & 0x0040) != 0) {
            sb.append("volatile ");
        }
        if ((acc & 0x0080) != 0) {
            sb.append("transient ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("synthetic ");
        }
        if ((acc & 0x4000) != 0) {
            sb.append("enum ");
        }
        return sb.toString();
    }

    @Override
    public JsonElement serialize(FieldObject f, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonFields = new JsonArray();
        String[][] fields = f.toStringMatrix();
        for (int i = 0; i < fields.length; i++) {
            JsonObject jsonField = new JsonObject();
            if (moreReadable) {
                jsonField.addProperty("field",
                        getAccessFlagString(Integer.valueOf(fields[i][2])) + " " + fields[i][1] + " " + fields[i][0]);
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
