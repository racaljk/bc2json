package classfile.attribute.typeannotation;

import classfile.adt.u1;
import classfile.adt.u2;
import parser.ClassFileReader;

import java.io.IOException;

public class TypeArgumentTarget extends Target {
    private u2 offset;
    private u1 typeArgumentIndex;

    public TypeArgumentTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        offset = read2Bytes();
        typeArgumentIndex = read1Byte();
    }

    public int getActualBytes() {
        return 3;
    }
}
