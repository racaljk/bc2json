package parser.classfile.attribute.typeannotation;

import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;

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
