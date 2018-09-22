package classfile.constantpool;

import adt.u1;
import adt.u2;
import bcm.ClassFileReader;
import classfile.constant.ConstantPoolTags;

import java.io.IOException;

public class ConstantMethodHandleInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_MethodHandle);
    public u1 referenceKind;
    public u2 referenceIndex;

    public ConstantMethodHandleInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        referenceKind = read1Byte();
        referenceIndex = read2Bytes();
    }
}
