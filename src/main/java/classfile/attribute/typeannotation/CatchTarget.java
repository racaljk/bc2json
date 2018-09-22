package classfile.attribute.typeannotation;

import adt.u2;
import bcm.ClassFileReader;

import java.io.IOException;


public class CatchTarget extends Target {
    private u2 exceptionTableIndex;

    public CatchTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        exceptionTableIndex = read2Bytes();
    }

    @Override
    public int getActualBytes() {
        return 2;
    }
}
