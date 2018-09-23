package serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataobject.ClassFileAttributeObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClassFileAttributeObjectSerializer implements JsonSerializer<ClassFileAttributeObject> {
    @Override
    public JsonElement serialize(ClassFileAttributeObject a, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray attrJson = new JsonArray();
        ArrayList<ArrayList<String>> res = a.toStringMatrix();

        for (ArrayList<String> arr : res) {
            JsonArray arrJson = new JsonArray();
            for (String str : arr) {
                arrJson.add(str);
            }
            attrJson.add(arrJson);
        }

        return attrJson;
    }
}
