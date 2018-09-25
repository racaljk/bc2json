package serializer;

import b2j.Option;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import parser.B2JRawClass;
import parser.classfile.constantpool.ConstantClassInfo;
import util.Readability;

import java.lang.reflect.Type;

public class B2JRawClassSerializer implements JsonSerializer<B2JRawClass> {
    private Option option;

    public B2JRawClassSerializer(Option option) {
        this.option = option;
    }

    @Override
    public JsonElement serialize(B2JRawClass b2JRawClass, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonRaw = new JsonObject();
        if (option.isMoreReadable()) {
            jsonRaw.addProperty("magic", "cafebabe");
            jsonRaw.addProperty("version", b2JRawClass.major_version.getValue() + "." + b2JRawClass.minor_version.getValue());
            if (!option.isIgnoreConstantPool()) {
                jsonRaw.add("constants", jsonSerializationContext.serialize(b2JRawClass.pool_slots));
            }
            jsonRaw.addProperty("access_flag", Readability.getClassAccessFlagString(b2JRawClass.access_flag.getValue()));
            jsonRaw.addProperty("this_class",
                    b2JRawClass.pool_slots.at(
                            ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.this_class.getValue())).nameIndex.getValue()
                    ).toString().replaceAll("/", ".")
            );

            jsonRaw.addProperty("super_class", b2JRawClass.super_class.getValue() != 0 ?
                    b2JRawClass.pool_slots.at(
                            ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.super_class.getValue())).nameIndex.getValue()
                    ).toString().replaceAll("/", ".")
                    : "java.lang.Object"
            );
        } else {
            jsonRaw.addProperty("magic", b2JRawClass.magic);
            jsonRaw.addProperty("minor_version", b2JRawClass.minor_version.getValue());
            jsonRaw.addProperty("major_version", b2JRawClass.major_version.getValue());
            if (!option.isIgnoreConstantPool()) {
                jsonRaw.add("constants", jsonSerializationContext.serialize(b2JRawClass.pool_slots));
            }
            jsonRaw.addProperty("access_flag", b2JRawClass.access_flag.getValue());
            jsonRaw.addProperty("this_class",
                    ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.this_class.getValue())).nameIndex.getValue()
            );
            jsonRaw.addProperty("super_class", b2JRawClass.super_class.getValue() != 0 ?
                    ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.super_class.getValue())).nameIndex.getValue() : 0
            );
        }

        if (!option.isIgnoreInterfaces()) {
            jsonRaw.add("interfaces", jsonSerializationContext.serialize(b2JRawClass.interfaces));
        }
        if (!option.isIgnoreFields()) {
            jsonRaw.add("fields", jsonSerializationContext.serialize(b2JRawClass.fields));
        }
        if (!option.isIgnoreMethods()) {
            jsonRaw.add("methods", jsonSerializationContext.serialize(b2JRawClass.methods));
        }
        if (!option.isIgnoreClassFileAttribute()) {
            jsonRaw.add("classfile_attributes", jsonSerializationContext.serialize(b2JRawClass.classfile_attributes));
        }
        return jsonRaw;
    }

}
