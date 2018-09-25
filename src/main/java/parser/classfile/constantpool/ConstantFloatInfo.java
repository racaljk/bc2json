package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u4;
import parser.classfile.constant.ConstantPoolTags;

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
