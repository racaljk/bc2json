package parser.classfile.attribute;

import parser.ClassFileReader;
import parser.classfile.adt.u2;

import java.io.IOException;

public class SourceFileAttribute extends Attribute {
    public u2 getSourceFileIndex() {
        return sourceFileIndex;
    }

    private u2 sourceFileIndex;

    public SourceFileAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        sourceFileIndex = read2Bytes();
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        return 2;
    }
}
