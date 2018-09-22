package classfile.constantpool;

import adt.u1;
import adt.u4;
import bcm.ClassFileReader;
import classfile.constant.ConstantPoolTags;

import java.io.IOException;

public class ConstantIntegerInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Integer);
    public u4 bytes;

    public ConstantIntegerInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        bytes = read4Bytes();
    }
}
