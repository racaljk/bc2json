package serialize;

import classfile.attribute.Attribute;
import classfile.attribute.InnerClassAttribute;
import classfile.attribute.SourceFileAttribute;
import com.google.gson.*;
import dataobject.ClassFileAttributeObject;

import java.lang.reflect.Type;

public class ClassFileAttributeObjectSerializer implements JsonSerializer<ClassFileAttributeObject> {
    @Override
    public JsonElement serialize(ClassFileAttributeObject a, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray attrJson = new JsonArray();


        for (Attribute attr : a.getAttributes()) {
            JsonObject oneAttrJson = new JsonObject();
            oneAttrJson.addProperty("attribute_name", SourceFileAttribute.class.getSimpleName());

            if (attr instanceof SourceFileAttribute) {
                oneAttrJson.addProperty("source_file", a.getCp().at(((SourceFileAttribute) attr).getSourceFileIndex().getValue()).toString());
            } else if (attr instanceof InnerClassAttribute) {
                //TODO:
            }

            attrJson.add(oneAttrJson);
        }
        return attrJson;
    }
}
