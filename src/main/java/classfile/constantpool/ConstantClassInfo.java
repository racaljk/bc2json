package classfile.constantpool;

import adt.u1;
import adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;


public class ConstantClassInfo extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Class);
    public u2 nameIndex;

    public ConstantClassInfo(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        nameIndex = read2Bytes();
    }

    @Override
    public String toString() {
        return "Class " + nameIndex.getValue();
    }
}
