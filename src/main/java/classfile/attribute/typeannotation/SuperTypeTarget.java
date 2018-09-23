package classfile.attribute.typeannotation;

import classfile.adt.u2;
import parser.ClassFileReader;

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
