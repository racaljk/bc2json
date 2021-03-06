package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u4;
import parser.classfile.constant.ConstantPoolTags;

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
