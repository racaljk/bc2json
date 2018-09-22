package classfile.constantpool;

import adt.u1;
import adt.u2;
import bcm.ClassFileReader;
import classfile.constant.ConstantPoolTags;

import java.io.IOException;

public class ConstantInvokeDynamicInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_InterfaceMethodref);
    public u2 bootstrapMethodAttrIndex;
    public u2 nameAndTypeIndex;

    public ConstantInvokeDynamicInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        bootstrapMethodAttrIndex = read2Bytes();
        nameAndTypeIndex = read2Bytes();
    }
}
