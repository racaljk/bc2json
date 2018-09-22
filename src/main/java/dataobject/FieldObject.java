package dataobject;

import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;
import classfile.field.FieldInfo;
import exception.ClassLoadingException;

import java.io.IOException;

public class FieldObject extends BytesReaderProxy implements Stuffable {
    private int fieldCount;
    private FieldInfo[] fields;

    private ConstantPoolObject cp;

    public FieldObject(ClassFileReader reader, ConstantPoolObject cpObject, int fieldCount) {
        super(reader);
        this.fieldCount = fieldCount;
        this.cp = cpObject;
        fields = new FieldInfo[fieldCount];
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        for (int i = 0; i < fieldCount; i++) {
            FieldInfo field = new FieldInfo(getReader(), cp);
            field.stuffing();
            fields[i] = field;
        }
    }

    public String[][] toStringMatrix() {
        String[][] f = new String[fieldCount][];
        for (int i = 0; i < fields.length; i++) {
            int fieldNameIndex = fields[i].nameIndex.getValue();//constant pool index,it's necessary for code execution engine later
            int fieldDescriptorIndex = fields[i].descriptorIndex.getValue();

            String[] str = new String[3];
            str[0] = cp.at(fieldNameIndex).toString();
            str[1] = cp.at(fieldDescriptorIndex).toString();
            str[2] = String.valueOf(fields[i].accessFlags.getValue());
            f[i] = str;
        }
        return f;
    }
}
