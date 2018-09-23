package classfile.attribute.typeannotation;

import classfile.adt.u2;
import parser.ClassFileReader;

import java.io.IOException;

public class ThrowsTarget extends Target {
    private u2 throwsTypeIndex;

    public ThrowsTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        throwsTypeIndex = read2Bytes();
    }

    public int getActualBytes() {
        return 2;
    }
}
