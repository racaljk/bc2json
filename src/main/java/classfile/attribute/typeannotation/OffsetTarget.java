package classfile.attribute.typeannotation;

import classfile.adt.u2;
import parser.ClassFileReader;

import java.io.IOException;

public class OffsetTarget extends Target {
    private u2 offset;

    public OffsetTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        offset = read2Bytes();
    }

    public int getActualBytes() {
        return 2;
    }
}
