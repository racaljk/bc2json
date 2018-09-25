package parser.classfile.attribute.typeannotation;

import parser.ClassFileReader;
import parser.classfile.adt.u2;

import java.io.IOException;


public class SuperTypeTarget extends Target {
    private u2 superTypeIndex;

    public SuperTypeTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        superTypeIndex = read2Bytes();
    }

    public int getActualBytes() {
        return 2;
    }
}
