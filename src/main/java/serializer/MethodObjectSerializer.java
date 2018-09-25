package serializer;

import b2j.Option;
import com.google.gson.*;
import dataobject.MethodObject;
import parser.classfile.constant.Mnemonic;
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
        CoreMethodProperties[] methods = m.getCoreMethodProperties();
        for (CoreMethodProperties method : methods) {
            JsonObject jsonMethod = new JsonObject();

            if (option.isMoreReadable()) {
                jsonMethod.addProperty("method_signature",
                        Readability.getMethodAccessFlagString(
                                Integer.valueOf(method.getAccessFlag())) +
                                Readability.peelMethodDescriptor(method.getMethodName(), method.getMethodDescriptor()));

                // If the method has opcode
                if (method.getCode() != null) {
                    StringBuilder sb = new StringBuilder();
                    String[] opcodes = method.getCode().split(",");
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

                        JsonArray exceptionsArrJson = new JsonArray();
                        for (int i = 0; i < method.getKnownExceptionNum(); i++) {
                            StringBuilder exceptionStrBuilder = new StringBuilder();
                            exceptionStrBuilder.append("@try block ranges from [");
                            exceptionStrBuilder.append(method.getExceptions()[i].getStartPc());
                            exceptionStrBuilder.append(",");
                            exceptionStrBuilder.append(method.getExceptions()[i].getEndPc());
                            exceptionStrBuilder.append(") and @handler at ");
                            exceptionStrBuilder.append(method.getExceptions()[i].getHandlerPc());
                            exceptionStrBuilder.append(" by the type of ");
                            exceptionStrBuilder.append(m.getCatchTypeString(method.getExceptions()[i].getCatchType()));
                            exceptionsArrJson.add(exceptionStrBuilder.toString());
                        }
                        jsonMethod.add("method_exceptions", exceptionsArrJson);
                    }
                }
            } else {
                jsonMethod.addProperty("method_name", method.getMethodName());
                jsonMethod.addProperty("method_type", method.getMethodDescriptor());
                jsonMethod.addProperty("method_flag", method.getAccessFlag());
                jsonMethod.addProperty("method_opcode", method.getCode());

                JsonArray exceptionsArrJson = new JsonArray();
                for (int i = 0; i < method.getKnownExceptionNum(); i++) {
                    JsonObject exceptionJson = new JsonObject();
                    exceptionJson.addProperty("start_pc", method.getExceptions()[i].getStartPc());
                    exceptionJson.addProperty("end_pc", method.getExceptions()[i].getEndPc());
                    exceptionJson.addProperty("handler_pc", method.getExceptions()[i].getHandlerPc());
                    exceptionJson.addProperty("catch_type", method.getExceptions()[i].getCatchType());
                    exceptionsArrJson.add(exceptionJson);
                }
                jsonMethod.add("method_exceptions", exceptionsArrJson);
            }
            jsonMethods.add(jsonMethod);
        }
        return jsonMethods;
    }
}
