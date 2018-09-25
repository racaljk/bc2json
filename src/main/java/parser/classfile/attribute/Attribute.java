package parser.classfile.attribute;

import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;
import parser.classfile.adt.u2;
import parser.classfile.adt.u4;

public abstract class Attribute extends BytesReaderProxy implements Stuffable, Verifiable {
    public u2 attributeNameIndex;
    protected u4 attributeLength;

    protected Attribute(ClassFileReader reader) {
        super(reader);
    }

    public final boolean isValidAttribute() {
        return attributeLength.getValue() == getActualBytes();
    }
}