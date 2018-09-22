package classfile.attribute;

import adt.u2;
import bcm.ClassFileReader;

import java.io.IOException;

public class EnclosingMethodAttribute extends Attribute {
    private u2 classIndex;
    private u2 methodIndex;

    public EnclosingMethodAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        classIndex = read2Bytes();
        methodIndex = read2Bytes();
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return 4;
    }
}
