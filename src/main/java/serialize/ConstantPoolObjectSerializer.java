package serialize;

import classfile.constantpool.*;
import com.google.gson.*;
import dataobject.ConstantPoolObject;
import util.Numeric;
import util.Readability;

import java.lang.reflect.Type;

public class ConstantPoolObjectSerializer implements JsonSerializer<ConstantPoolObject> {
    private boolean moreReadable;

    public ConstantPoolObjectSerializer(boolean moreReadable) {
        this.moreReadable = moreReadable;
    }

    @Override
    public JsonElement serialize(ConstantPoolObject cp, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonObject pool = new JsonObject();

        // It's not necessary
        if (!moreReadable) {
            pool.addProperty("slotsNum", String.valueOf(cp.getSlotsNum().getValue()));
        }

        JsonArray jsonSlots = new JsonArray();
        for (AbstractConstantPool x : cp.getSlots()) {
            JsonObject jsonSlot = new JsonObject();
            jsonSlot.addProperty("index", x.getTableIndex());
            jsonSlot.addProperty("type", x.getClass().getTypeName());

            if (x instanceof ConstantClassInfo) {
                String qualifiedClassName = cp.at(((ConstantClassInfo) x).nameIndex.getValue()).toString();
                jsonSlot.addProperty("class_name",
                        moreReadable ? qualifiedClassName.replaceAll("/", ".") : qualifiedClassName);
            } else if (x instanceof ConstantFieldRefInfo) {
                int classIndex = ((ConstantFieldRefInfo) x).classIndex.getValue();
                String qualifiedClassName = cp.at(((ConstantClassInfo) cp.at(classIndex)).nameIndex.getValue()).toString();

                int nameAndTypeIndex = ((ConstantFieldRefInfo) x).nameAndTypeIndex.getValue();
                String name = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).nameIndex.getValue()).toString();
                String type = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).descriptorIndex.getValue()).toString();

                if (moreReadable) {
                    String field = Readability.peelFieldDescriptor(type) + " " +
                            qualifiedClassName.replaceAll("/", ".") + "::" +
                            name;
                    jsonSlot.addProperty("field", field);
                } else {
                    jsonSlot.addProperty("field_class", qualifiedClassName);
                    jsonSlot.addProperty("field_name", name);
                    jsonSlot.addProperty("field_type", type);
                }

            } else if (x instanceof ConstantMethodRefInfo) {
                int classIndex = ((ConstantMethodRefInfo) x).classIndex.getValue();
                String qualifiedClassName = cp.at(((ConstantClassInfo) cp.at(classIndex)).nameIndex.getValue()).toString();

                int nameAndTypeIndex = ((ConstantMethodRefInfo) x).nameAndTypeIndex.getValue();
                String name = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).nameIndex.getValue()).toString();
                String type = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).descriptorIndex.getValue()).toString();

                if (moreReadable) {
                    String method = Readability.peelMethodDescriptor(name, type);
                    jsonSlot.addProperty("method_class", qualifiedClassName.replaceAll("/", "."));
                    jsonSlot.addProperty("method", method);
                } else {
                    jsonSlot.addProperty("method_class", qualifiedClassName);
                    jsonSlot.addProperty("method_name", name);
                    jsonSlot.addProperty("method_type", type);
                }
            } else if (x instanceof ConstantInterfaceMethodRefInfo) {
                int classIndex = ((ConstantInterfaceMethodRefInfo) x).classIndex.getValue();
                String qualifiedClassName = cp.at(((ConstantClassInfo) cp.at(classIndex)).nameIndex.getValue()).toString();

                int nameAndTypeIndex = ((ConstantInterfaceMethodRefInfo) x).nameAndTypeIndex.getValue();
                String name = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).nameIndex.getValue()).toString();
                String type = cp.at(((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).descriptorIndex.getValue()).toString();

                jsonSlot.addProperty("interface_class", qualifiedClassName);
                jsonSlot.addProperty("interface_name", name);
                jsonSlot.addProperty("interface_type", type);
            } else if (x instanceof ConstantStringInfo) {
                String literalString = cp.at(((ConstantStringInfo) x).stringIndex.getValue()).toString();

                jsonSlot.addProperty("string", literalString);
            } else if (x instanceof ConstantIntegerInfo) {
                int integerV = (int) ((ConstantIntegerInfo) x).bytes.getValue();

                jsonSlot.addProperty("value", integerV);
            } else if (x instanceof ConstantFloatInfo) {
                int bits = (int) ((ConstantFloatInfo) x).bytes.getValue();

                jsonSlot.addProperty("value", Numeric.resolveFloat(bits));
            } else if (x instanceof ConstantLongInfo) {
                int high = (int) ((ConstantLongInfo) x).highBytes.getValue();
                int low = (int) ((ConstantLongInfo) x).lowBytes.getValue();
                long longV = ((long) high << 32) + low;

                jsonSlot.addProperty("value", longV);
            } else if (x instanceof ConstantDoubleInfo) {
                int high = (int) ((ConstantDoubleInfo) x).highBytes.getValue();
                int low = (int) ((ConstantDoubleInfo) x).lowBytes.getValue();

                jsonSlot.addProperty("value", Numeric.resolveDouble(high, low));
            } else if (x instanceof ConstantNameAndTypeInfo) {
                int nameIndex = ((ConstantNameAndTypeInfo) x).nameIndex.getValue();
                int descriptorIndex = ((ConstantNameAndTypeInfo) x).descriptorIndex.getValue();
                String name = cp.at(nameIndex).toString();
                String descriptor = cp.at(descriptorIndex).toString();

                jsonSlot.addProperty("name", name);
                jsonSlot.addProperty("descriptor", descriptor);
            } else if (x instanceof ConstantUtf8Info) {
                jsonSlot.addProperty("utf8", x.toString());
            } else if (x instanceof ConstantMethodHandleInfo) {
                int referenceKind = ((ConstantMethodHandleInfo) x).referenceKind.getValue();
                int referenceIndex = ((ConstantMethodHandleInfo) x).referenceIndex.getValue();

                jsonSlot.addProperty("reference_kind", referenceKind);
                jsonSlot.addProperty("reference_index", referenceIndex);
                // TODO: we can provide more information about reference_index
            } else if (x instanceof ConstantMethodTypeInfo) {
                int descriptorIndex = ((ConstantMethodTypeInfo) x).descriptorIndex.getValue();
                String descriptor = cp.at(descriptorIndex).toString();

                jsonSlot.addProperty("descriptor", descriptor);
            } else if (x instanceof ConstantInvokeDynamicInfo) {
                int bootstrapMethodAttributeIndex = ((ConstantInvokeDynamicInfo) x).bootstrapMethodAttrIndex.getValue();
                int nameAndTypeIndex = ((ConstantInvokeDynamicInfo) x).nameAndTypeIndex.getValue();
                int nameIndex = ((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).nameIndex.getValue();
                int typeIndex = ((ConstantNameAndTypeInfo) cp.at(nameAndTypeIndex)).descriptorIndex.getValue();
                String name = cp.at(nameIndex).toString();
                String type = cp.at(typeIndex).toString();

                jsonSlot.addProperty("bootstrap_method_index", bootstrapMethodAttributeIndex);
                jsonSlot.addProperty("name", name);
                jsonSlot.addProperty("type", type);
            } else {
                throw new RuntimeException("Unrecognized constant pool slot");
            }

            jsonSlots.add(jsonSlot);
        }

        pool.add("slots", jsonSlots);
        return pool;
    }
}
