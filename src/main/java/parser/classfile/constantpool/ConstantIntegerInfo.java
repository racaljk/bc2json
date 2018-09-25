package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u4;
import parser.classfile.constant.ConstantPoolTags;

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
