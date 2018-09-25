package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.constant.ConstantPoolTags;

import java.io.IOException;


public class ConstantFieldRefInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Fieldref);
    public u2 classIndex;
    public u2 nameAndTypeIndex;

    public ConstantFieldRefInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        classIndex = read2Bytes();
        nameAndTypeIndex = read2Bytes();
    }
}
