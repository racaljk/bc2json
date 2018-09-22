package classfile.attribute;

import adt.u2;
import adt.u4;
import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;

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