package serialize;

import classfile.constantpool.ConstantClassInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import parser.B2JRawClass;

import java.lang.reflect.Type;

public class B2JRawClassSerializer implements JsonSerializer<B2JRawClass> {
    private boolean moreReadable;

    public B2JRawClassSerializer(boolean moreReadable) {
        this.moreReadable = moreReadable;
    }

    @Override
    public JsonElement serialize(B2JRawClass b2JRawClass, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonRaw = new JsonObject();
        if (moreReadable) {
            jsonRaw.addProperty("magic", "cafebabe");
            jsonRaw.addProperty("version", b2JRawClass.major_version.getValue() + "." + b2JRawClass.minor_version.getValue());
            jsonRaw.add("constants", jsonSerializationContext.serialize(b2JRawClass.pool_slots));
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
            jsonRaw.add("constants", jsonSerializationContext.serialize(b2JRawClass.pool_slots));
            jsonRaw.addProperty("access_flag", b2JRawClass.access_flag.getValue());
            jsonRaw.addProperty("this_class",
                    ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.this_class.getValue())).nameIndex.getValue()
            );
            jsonRaw.addProperty("super_class",
                    ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.super_class.getValue())).nameIndex.getValue()
            );
        }


        jsonRaw.add("interfaces", jsonSerializationContext.serialize(b2JRawClass.interfaces));
        jsonRaw.add("fields", jsonSerializationContext.serialize(b2JRawClass.fields));
        jsonRaw.add("methods", jsonSerializationContext.serialize(b2JRawClass.methods));
        jsonRaw.add("classfile_attributes", jsonSerializationContext.serialize(b2JRawClass.classfile_attributes));
        return jsonRaw;
    }

}
