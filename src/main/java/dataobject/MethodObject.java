package dataobject;

import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;
import parser.classfile.adt.u1;
import parser.classfile.attribute.CodeAttribute;
import parser.classfile.constantpool.ConstantClassInfo;
import parser.classfile.exception.ClassLoadingException;
import parser.classfile.field.FieldInfo;
import serializer.CoreMethodProperties;

import java.io.IOException;

public class MethodObject extends BytesReaderProxy implements Stuffable {
    private FieldInfo[] methods;
    private ConstantPoolObject cp;

    public MethodObject(ClassFileReader reader, ConstantPoolObject cpObject, int methodCount) {
        super(reader);
        this.cp = cpObject;
        methods = new FieldInfo[methodCount];
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        for (int i = 0; i < methods.length; i++) {
            FieldInfo field = new FieldInfo(getReader(), cp);
            field.stuffing();
            methods[i] = field;
        }
    }

    public CoreMethodProperties[] getCoreMethodProperties() {
        CoreMethodProperties[] coreMethods = new CoreMethodProperties[methods.length];

        for (int i = 0; i < methods.length; i++) {
            CoreMethodProperties coreMethod = new CoreMethodProperties();
            int methodNameIndex = methods[i].nameIndex.getValue();//constant pool index,it's necessary for code execution engine later
            int methodDescriptorIndex = methods[i].descriptorIndex.getValue();

            coreMethod.setMethodName(cp.at(methodNameIndex).toString());
            coreMethod.setMethodDescriptor(cp.at(methodDescriptorIndex).toString());
            coreMethod.setAccessFlag(String.valueOf(methods[i].accessFlags.getValue()));

            for (int k = 0; k < methods[i].attributeCount.getValue(); k++) {
                if (methods[i].attributes[k] instanceof CodeAttribute) {
                    coreMethod.setCode(arrayJoin(((CodeAttribute) methods[i].attributes[k]).code));
                    coreMethod.setMaxStack(((CodeAttribute) methods[i].attributes[k]).maxStack.getValue());
                    coreMethod.setMaxLocal(((CodeAttribute) methods[i].attributes[k]).maxLocals.getValue());

                    coreMethod.setKnownExceptionNum(((CodeAttribute) methods[i].attributes[k]).exceptionTableLength.getValue());
                    if (coreMethod.getKnownExceptionNum() == 0) {
                        continue;
                    }
                    CoreMethodProperties.KnownException[] exceptions = new CoreMethodProperties.KnownException[
                            ((CodeAttribute) methods[i].attributes[k]).exceptionTableLength.getValue()
                            ];
                    for (int p = 0; p < ((CodeAttribute) methods[i].attributes[k]).exceptionTableLength.getValue(); p++) {
                        exceptions[p] = new CoreMethodProperties.KnownException();
                        exceptions[p].setStartPc(((CodeAttribute) methods[i].attributes[k]).table[p].startPc.getValue());
                        exceptions[p].setEndPc(((CodeAttribute) methods[i].attributes[k]).table[p].endPc.getValue());
                        exceptions[p].setCatchType(((CodeAttribute) methods[i].attributes[k]).table[p].catchType.getValue());
                        exceptions[p].setHandlerPc(((CodeAttribute) methods[i].attributes[k]).table[p].handlerPc.getValue());
                    }
                    coreMethod.setExceptions(exceptions);
                }
            }
            coreMethods[i] = coreMethod;
        }
        return coreMethods;
    }

    private String arrayJoin(u1[] arr) {
        if (arr.length == 0) {
            return "";
        }
        if (arr.length == 1) {
            return String.valueOf(arr[0].getValue());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i].getValue());
            sb.append(",");
        }
        sb.append(arr[arr.length - 1].getValue());
        return sb.toString();
    }

    public String getCatchTypeString(int catchType) {
        if (catchType != 0) {
            return cp.at(((ConstantClassInfo) cp.at(catchType)).nameIndex.getValue()).toString().replaceAll("/", ".");
        } else {
            return "all exceptions";
        }
    }
}