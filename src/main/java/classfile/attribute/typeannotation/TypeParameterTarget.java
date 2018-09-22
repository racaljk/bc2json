package classfile.attribute.typeannotation;

import adt.u1;
import bcm.ClassFileReader;

import java.io.IOException;


public class TypeParameterTarget extends Target {
    private u1 typeParameterIndex;

    public TypeParameterTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        typeParameterIndex = read1Byte();
    }

    public int getActualBytes() {
        return 1;
    }
}
