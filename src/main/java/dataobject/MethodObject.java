package dataobject;

import adt.u1;
import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;
import classfile.attribute.CodeAttribute;
import classfile.field.FieldInfo;
import exception.ClassLoadingException;

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

    public String[][] toStringMatrix() {
        String[][] m = new String[methods.length][];
        for (int i = 0; i < methods.length; i++) {
            int methodNameIndex = methods[i].nameIndex.getValue();//constant pool index,it's necessary for code execution engine later
            int methodDescriptorIndex = methods[i].descriptorIndex.getValue();

            String[] str = new String[4];
            str[0] = cp.at(methodNameIndex).toString();
            str[1] = cp.at(methodDescriptorIndex).toString();
            str[2] = String.valueOf(methods[i].accessFlags.getValue());

            for (int k = 0; k < methods[i].attributeCount.getValue(); k++) {
                if (methods[i].attributes[k] instanceof CodeAttribute) {
                    str[3] = arrayJoin(((CodeAttribute) methods[i].attributes[k]).code);
                }
            }
            m[i] = str;
        }
        return m;
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
}