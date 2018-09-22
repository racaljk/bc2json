package classfile.attribute.typeannotation;

import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;
import classfile.attribute.Verifiable;

public abstract class Target extends BytesReaderProxy implements Stuffable, Verifiable {
    Target(ClassFileReader reader) {
        super(reader);
    }
}
