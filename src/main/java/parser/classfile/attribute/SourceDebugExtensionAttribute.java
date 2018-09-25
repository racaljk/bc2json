package parser.classfile.attribute;

import parser.ClassFileReader;
import parser.classfile.adt.u1;

import java.io.IOException;

public class SourceDebugExtensionAttribute extends Attribute {
    public u1[] debugExtension;

    public SourceDebugExtensionAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        debugExtension = new u1[(int) attributeLength.getValue()];
        for (int i = 0; i < debugExtension.length; i++) {
            debugExtension[i] = read1Byte();
        }
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return debugExtension.length;
    }
}
