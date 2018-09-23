package classfile.constantpool;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

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
