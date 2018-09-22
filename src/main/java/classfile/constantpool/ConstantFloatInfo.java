package classfile.constantpool;

import adt.u1;
import adt.u4;
import bcm.ClassFileReader;
import classfile.constant.ConstantPoolTags;

import java.io.IOException;


public class ConstantFloatInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Float);
    public u4 bytes;

    public ConstantFloatInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        bytes = read4Bytes();
    }
}
