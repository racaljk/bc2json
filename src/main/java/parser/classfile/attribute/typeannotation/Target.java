package parser.classfile.attribute.typeannotation;

import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;
import parser.classfile.attribute.Verifiable;

public abstract class Target extends BytesReaderProxy implements Stuffable, Verifiable {
    Target(ClassFileReader reader) {
        super(reader);
    }
}
