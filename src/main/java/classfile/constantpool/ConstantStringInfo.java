package classfile.constantpool;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantStringInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_String);
    public u2 stringIndex;

    public ConstantStringInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        stringIndex = read2Bytes();
    }
}
