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
                JsonArray innerClassArrJson = new JsonArray();
                for (int i = 0; i < ((InnerClassAttribute) attr).numberOfClasses.getValue(); i++) {
                    JsonObject innerClassJson = new JsonObject();
                    innerClassJson.addProperty("inner_class_index",
                            ((InnerClassAttribute) attr).classes[i].innerClassInfoIndex.getValue()
                    );
                    innerClassJson.addProperty("outer_class_index",
                            ((InnerClassAttribute) attr).classes[i].outerClassInfoIndex.getValue()
                    );
                    innerClassJson.addProperty("inner_name_index",
                            ((InnerClassAttribute) attr).classes[i].innerNameIndex.getValue()
                    );
                    innerClassJson.addProperty("inner_class_access_flag",
                            ((InnerClassAttribute) attr).classes[i].innerClassAccessFlags.getValue()
                    );
                    innerClassArrJson.add(innerClassJson);
                }
                oneAttrJson.add("inner_classes", innerClassArrJson);
            }

            attrJson.add(oneAttrJson);
        }
        return attrJson;
    }
}
