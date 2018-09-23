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
            jsonRaw.addProperty("access_flag", getAccessFlagString(b2JRawClass.access_flag.getValue()));

        } else {
            jsonRaw.addProperty("magic", b2JRawClass.magic);
            jsonRaw.addProperty("minor_version", b2JRawClass.minor_version.getValue());
            jsonRaw.addProperty("major_version", b2JRawClass.major_version.getValue());
            jsonRaw.add("constants", jsonSerializationContext.serialize(b2JRawClass.pool_slots));
            jsonRaw.addProperty("access_flag", b2JRawClass.access_flag.getValue());
        }

        jsonRaw.addProperty("this_class",
                b2JRawClass.pool_slots.at(
                        ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.this_class.getValue())).nameIndex.getValue()
                ).toString()
        );

        jsonRaw.addProperty("super_class", b2JRawClass.super_class.getValue() != 0 ?
                b2JRawClass.pool_slots.at(
                        ((ConstantClassInfo) b2JRawClass.pool_slots.at(b2JRawClass.super_class.getValue())).nameIndex.getValue()
                ).toString()
                : "java/lang/Object"
        );
        jsonRaw.add("interfaces", jsonSerializationContext.serialize(b2JRawClass.interfaces));
        jsonRaw.add("fields", jsonSerializationContext.serialize(b2JRawClass.fields));
        jsonRaw.add("methods", jsonSerializationContext.serialize(b2JRawClass.methods));
        jsonRaw.add("classfile_attributes", jsonSerializationContext.serialize(b2JRawClass.classfile_attributes));
        return jsonRaw;
    }

    private String getAccessFlagString(int acc) {
        StringBuilder sb = new StringBuilder();

        if ((acc & 0x0001) != 0) {
            sb.append("ACC_PUBLIC ");
        }
        if ((acc & 0x0010) != 0) {
            sb.append("ACC_FINAL ");
        }
        if ((acc & 0x0020) != 0) {
            sb.append("ACC_SUPER ");
        }
        if ((acc & 0x0200) != 0) {
            sb.append("ACC_INTERFACE ");
        }
        if ((acc & 0x0400) != 0) {
            sb.append("ACC_ABSTRACT ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("ACC_SYNTHETIC ");
        }
        if ((acc & 0x2000) != 0) {
            sb.append("ACC_ANNOTATION ");
        }
        if ((acc & 0x4000) != 0) {
            sb.append("ACC_ENUM ");
        }
        return sb.toString();
    }
}
