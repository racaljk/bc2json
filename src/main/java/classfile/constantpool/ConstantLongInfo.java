package classfile.constantpool;

import adt.u1;
import adt.u4;
import bcm.ClassFileReader;
import classfile.constant.ConstantPoolTags;

import java.io.IOException;

public class ConstantLongInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Long);
    public u4 highBytes;
    public u4 lowBytes;

    public ConstantLongInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        highBytes = read4Bytes();
        lowBytes = read4Bytes();
    }
}
