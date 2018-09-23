package classfile.constantpool;

import classfile.adt.u1;
import classfile.adt.u4;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantDoubleInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Double);
    public u4 highBytes;
    public u4 lowBytes;

    public ConstantDoubleInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        highBytes = read4Bytes();
        lowBytes = read4Bytes();
    }
}
