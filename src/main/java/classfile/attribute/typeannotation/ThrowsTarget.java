package classfile.attribute.typeannotation;

import adt.u2;
import bcm.ClassFileReader;

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