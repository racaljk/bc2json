package classfile.attribute;

import parser.ClassFileReader;

import java.io.IOException;

public class DeprecatedAttribute extends Attribute {
    public DeprecatedAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();

    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return 0;
    }
}
