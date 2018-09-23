package classfile.constantpool;

import adt.u1;
import adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantMethodRefInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Methodref);
    public u2 classIndex;
    public u2 nameAndTypeIndex;

    public ConstantMethodRefInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        classIndex = read2Bytes();
        nameAndTypeIndex = read2Bytes();
    }
}
