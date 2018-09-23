package classfile.attribute;

import classfile.adt.u2;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantValueAttribute extends Attribute {
    private u2 constantValueIndex;

    public ConstantValueAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        constantValueIndex = read2Bytes();
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return 2;
    }
}
