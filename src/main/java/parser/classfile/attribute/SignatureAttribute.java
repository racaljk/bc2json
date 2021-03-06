package parser.classfile.attribute;

import parser.ClassFileReader;
import parser.classfile.adt.u2;

import java.io.IOException;

public class SignatureAttribute extends Attribute {
    public u2 signatureIndex;

    public SignatureAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        signatureIndex = read2Bytes();
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return 2;
    }
}
