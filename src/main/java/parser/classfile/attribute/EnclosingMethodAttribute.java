package parser.classfile.attribute;

import parser.ClassFileReader;
import parser.classfile.adt.u2;

import java.io.IOException;

public class EnclosingMethodAttribute extends Attribute {
    public u2 classIndex;
    public u2 methodIndex;

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
