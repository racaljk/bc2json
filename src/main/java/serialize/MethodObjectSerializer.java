package serialize;

import b2j.Option;
import classfile.constant.Mnemonic;
import com.google.gson.*;
import dataobject.MethodObject;
import util.Readability;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class MethodObjectSerializer implements JsonSerializer<MethodObject> {
    private Option option;

    public MethodObjectSerializer(Option option) {
        this.option = option;
    }


    @Override
    public JsonElement serialize(MethodObject m, Type serializeType, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonMethods = new JsonArray();
        String[][] methods = m.toStringMatrix();
        for (String[] method : methods) {
            JsonObject jsonMethod = new JsonObject();

            if (option.isMoreReadable()) {
                jsonMethod.addProperty("method_signature", Readability.getMethodAccessFlagString(Integer.valueOf(method[2])) + Readability.peelMethodDescriptor(method[0], method[1]));

                // If the method has opcode
                if (method[3] != null) {
                    StringBuilder sb = new StringBuilder();
                    String[] opcodes = method[3].split(",");
                    Field[] mnemonicFields = Mnemonic.class.getDeclaredFields();
                    for (String opcode : opcodes) {
                        for (Field mnemonicField : mnemonicFields) {
                            try {
                                if (mnemonicField.getInt(null) == Integer.valueOf(opcode)) {
                                    sb.append(mnemonicField.getName().replace("$", ""));
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
