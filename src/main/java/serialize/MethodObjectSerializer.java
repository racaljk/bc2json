package serialize;

import classfile.constant.Mnemonic;
import com.google.gson.*;
import dataobject.MethodObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class MethodObjectSerializer implements JsonSerializer<MethodObject> {
    private boolean moreReadable;

    public MethodObjectSerializer(boolean moreReadable) {
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
        if ((acc & 0x0020) != 0) {
            sb.append("synchronized ");
        }
        if ((acc & 0x0040) != 0) {
            sb.append("bridge ");
        }
        if ((acc & 0x0080) != 0) {
            sb.append("varargs ");
        }
        if ((acc & 0x0100) != 0) {
            sb.append("native ");
        }
        if ((acc & 0x0400) != 0) {
            sb.append("abstract ");
        }
        if ((acc & 0x0800) != 0) {
            sb.append("strict ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("synthetic ");
        }
        return sb.toString();
    }

    @Override
    public JsonElement serialize(MethodObject m, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonMethods = new JsonArray();
        String[][] methods = m.toStringMatrix();
        for (String[] method : methods) {
            JsonObject jsonMethod = new JsonObject();

            if (moreReadable) {
                jsonMethod.addProperty("method_signature", getAccessFlagString(Integer.valueOf(method[2])) + " " + method[0] + " " + method[1]);
                StringBuilder sb = new StringBuilder();
                String[] opcodes = method[3].split(",");
                Field[] mnemonicFields = Mnemonic.class.getDeclaredFields();
                for (int i = 0; i < opcodes.length; i++) {
                    for (int k = 0; k < mnemonicFields.length; k++) {
                        try {
                            if (mnemonicFields[k].getInt(null) == Integer.valueOf(opcodes[i])) {
                                sb.append(mnemonicFields[k].getName());
                                sb.append(",");
                                break;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (sb.toString().length() > 0) {
                    jsonMethod.addProperty("method_opcode", sb.toString().substring(0, sb.toString().length() - 1));
                }

            } else {
                jsonMethod.addProperty("method_name", method[0]);
                jsonMethod.addProperty("method_type", method[1]);
                jsonMethod.addProperty("method_flag", method[2]);
                jsonMethod.addProperty("method_opcode", method[3]);
            }
            jsonMethods.add(jsonMethod);
        }
        return jsonMethods;
    }
}
