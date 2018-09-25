package parser.classfile.constantpool;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.constant.ConstantPoolTags;

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
