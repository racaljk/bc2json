package classfile.constantpool;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantMethodTypeInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_MethodType);
    public u2 descriptorIndex;

    public ConstantMethodTypeInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        descriptorIndex = read2Bytes();
    }
}
