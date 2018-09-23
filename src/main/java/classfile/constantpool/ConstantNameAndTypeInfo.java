package classfile.constantpool;

import adt.u1;
import adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantNameAndTypeInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_NameAndType);
    public u2 nameIndex;
    public u2 descriptorIndex;

    public ConstantNameAndTypeInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        nameIndex = read2Bytes();
        descriptorIndex = read2Bytes();
    }
}
