package serialize;

import classfile.attribute.*;
import com.google.gson.*;
import dataobject.ClassFileAttributeObject;

import java.lang.reflect.Type;

public class ClassFileAttributeObjectSerializer implements JsonSerializer<ClassFileAttributeObject> {
    @Override
    public JsonElement serialize(ClassFileAttributeObject a, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray attrJson = new JsonArray();


        for (Attribute attr : a.getAttributes()) {
            JsonObject oneAttrJson = new JsonObject();
            oneAttrJson.addProperty("attribute_name", attr.getClass().getSimpleName());

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
            } else if (attr instanceof EnclosingMethodAttribute) {
                oneAttrJson.addProperty("class_index", ((EnclosingMethodAttribute) attr).classIndex.getValue());
                oneAttrJson.addProperty("method_index", ((EnclosingMethodAttribute) attr).methodIndex.getValue());
            } else if (attr instanceof SourceDebugExtensionAttribute) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < ((SourceDebugExtensionAttribute) attr).debugExtension.length; i++) {
                    sb.append(((SourceDebugExtensionAttribute) attr).debugExtension[i].getValue());
                    sb.append(",");
                }
                oneAttrJson.addProperty("debug_extension", sb.toString());
            } else if (attr instanceof SyntheticAttribute || attr instanceof DeprecatedAttribute) {
                //THERE ARE NO PROPERTIES
            } else if (attr instanceof SignatureAttribute) {
                oneAttrJson.addProperty("signature_index", ((SignatureAttribute) attr).signatureIndex.getValue());
            }
            /*
            TODO: CONSIDER TO SUPPORT THEM
            else if(attr instanceof RuntimeVisibleAnnotationsAttribute){
            }else if(attr instanceof RuntimeInvisibleAnnotationsAttribute){
            }else if(attr instanceof RuntimeVisibleTypeAnnotationsAttribute){
            }else if(attr instanceof RuntimeInvisibleTypeAnnotationsAttribute){
            }else if(attr instanceof BootstrapMethodsAttribute){
            }*/
            attrJson.add(oneAttrJson);
        }
        return attrJson;
    }
}
