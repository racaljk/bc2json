package serializer;

import b2j.Option;
import com.google.gson.*;
import dataobject.MethodObject;
import parser.classfile.constant.Mnemonic;
import util.Numeric;
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
                    int[] opcodes = method.getCode();

                    for(int i=0;i<opcodes.length;i++){
                        int opcode=opcodes[i];
                        sb.append(Readability.opcode2MnemonicStr(opcode));

                        // read extra operand for specific opcodes
                        switch (opcode) {
                            case Mnemonic.aaload: case Mnemonic.astore: case Mnemonic.bipush: case Mnemonic.dload: case Mnemonic.dstore:
                            case Mnemonic.fload: case Mnemonic.fstore: case Mnemonic.iload: case Mnemonic.istore: case Mnemonic.ldc:
                            case Mnemonic.lload: case Mnemonic.lstore: case Mnemonic.newarray: case Mnemonic.ret:
                                sb.append(" #").append(opcodes[++i]);
                                break;
                            case Mnemonic.anewarray: case Mnemonic.checkcast: case Mnemonic.getfield: case Mnemonic.getstatic: case Mnemonic.goto$:
                            case Mnemonic.if_acmpeq: case Mnemonic.if_acmpne: case Mnemonic.if_icmpeq: case Mnemonic.if_icmpge: case Mnemonic.if_icmpgt:
                            case Mnemonic.if_icmple: case Mnemonic.if_icmplt: case Mnemonic.if_icmpne: case Mnemonic.ifeq: case Mnemonic.ifge:
                            case Mnemonic.ifgt: case Mnemonic.ifle: case Mnemonic.iflt: case Mnemonic.ifne: case Mnemonic.ifnonnull: case Mnemonic.ifnull:
                            case Mnemonic.instanceof$: case Mnemonic.invokespecial: case Mnemonic.invokestatic: case Mnemonic.invokevirtual: case Mnemonic.jsr:
                            case Mnemonic.ldc_w: case Mnemonic.ldc2_w: case Mnemonic.new$: case Mnemonic.putfield: case Mnemonic.putstatic: case Mnemonic.sipush:
                                sb.append(" #").append(Numeric.operandCompose(opcodes[++i],opcodes[++i]));
                                break;
                            case Mnemonic.goto_w: case Mnemonic.jsr_w:
                                sb.append(" #").append(Numeric.operandCompose(opcodes[++i],opcodes[++i],
                                        opcodes[++i],opcodes[++i]));
                                break;
                            // special cases:
                            case Mnemonic.lookupswitch: {
                                i += (((i+1+3) & ~3)-i);
                                int defaultBranch = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                        opcodes[i++], opcodes[i++]);
                                sb.append(" default#").append(defaultBranch);
                                int npair = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                        opcodes[i++], opcodes[i++]);
                                if (npair > 0) {
                                    sb.append("match#");
                                }
                                for (int p = 0; p < npair; p++) {
                                    int match = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                            opcodes[i++], opcodes[i++]);
                                    int offset = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                            opcodes[i++], opcodes[i++]);
                                    sb.append("{").append(match).append(":").append(offset).append("}");
                                }
                                break;
                            }
                            case Mnemonic.tableswitch: {
                                i += (((i+1+3) & ~3)-i);
                                int defaultBranch = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                        opcodes[i++], opcodes[i++]);
                                sb.append(" default#").append(defaultBranch);
                                int low = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                        opcodes[i++], opcodes[i++]);
                                int high = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                        opcodes[i++], opcodes[i++]);
                                if (high - low +1 > 0) {
                                    sb.append("match#");
                                }
                                for (int p = 0; p < high - low +1; p++) {
                                    int offset = Numeric.operandCompose(opcodes[i++], opcodes[i++],
                                            opcodes[i++], opcodes[i++]);
                                    sb.append("{").append(p).append(":").append(offset).append("}");
                                }
                                break;
                            }
                            case Mnemonic.wide:
                                int operand1 = opcodes[++i];
                                if(operand1==Mnemonic.iinc){
                                    sb.append(" #")
                                            .append(Numeric.operandCompose(opcodes[++i],opcodes[++i]))
                                            .append(" #")
                                            .append(Numeric.operandCompose(opcodes[++i],opcodes[++i]));
                                }else{
                                    sb.append(" #")
                                            .append(Numeric.operandCompose(opcodes[++i],opcodes[++i]));
                                }
                                break;
                            case Mnemonic.iinc:
                                sb.append(" #").append(opcodes[++i]).append(" #").append(opcodes[++i]);
                                break;
                            case Mnemonic.invokedynamic:
                                sb.append(" #").append(Numeric.operandCompose(opcodes[++i],opcodes[++i]));
                                i+=2;
                                break;
                            case Mnemonic.invokeinterface:
                                sb.append(" #")
                                        .append(Numeric.operandCompose(opcodes[++i],opcodes[++i]))
                                        .append(" #").append(opcodes[++i]);
                                ++i;
                                break;
                            case Mnemonic.multianewarray:
                                sb.append(" #").append(Numeric.operandCompose(opcodes[++i],opcodes[++i]))
                                        .append(" #").append(opcodes[++i]);
                                break;
                        }

                        sb.append(",");
                    }


                    if (sb.toString().length() > 0) {
                        jsonMethod.addProperty("method_opcode", sb.toString().substring(0, sb.toString().length() - 1));

                        JsonArray exceptionsArrJson = new JsonArray();
                        for (int i = 0; i < method.getKnownExceptionNum(); i++) {
                            String exceptionStrBuilder = "@try block ranges from [" +
                                    method.getExceptions()[i].getStartPc() +
                                    "," +
                                    method.getExceptions()[i].getEndPc() +
                                    ") and @handler at " +
                                    method.getExceptions()[i].getHandlerPc() +
                                    " by the type of " +
                                    m.getCatchTypeString(method.getExceptions()[i].getCatchType());
                            exceptionsArrJson.add(exceptionStrBuilder);
                        }
                        jsonMethod.add("method_exceptions", exceptionsArrJson);
                    }
                }
            } else {
                jsonMethod.addProperty("method_name", method.getMethodName());
                jsonMethod.addProperty("method_type", method.getMethodDescriptor());
                jsonMethod.addProperty("method_flag", method.getAccessFlag());
                jsonMethod.addProperty("method_opcode", method.getStringifiedCode());

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
