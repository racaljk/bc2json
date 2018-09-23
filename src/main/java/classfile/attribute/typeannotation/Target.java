package classfile.attribute.typeannotation;

import classfile.attribute.Verifiable;
import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;

public abstract class Target extends BytesReaderProxy implements Stuffable, Verifiable {
    Target(ClassFileReader reader) {
        super(reader);
    }
}
