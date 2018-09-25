package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.constant.ConstantPoolTags;

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
