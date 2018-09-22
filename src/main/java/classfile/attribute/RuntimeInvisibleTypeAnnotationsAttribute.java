package classfile.attribute;

import bcm.ClassFileReader;

public class RuntimeInvisibleTypeAnnotationsAttribute extends RuntimeVisibleTypeAnnotationsAttribute {

    public RuntimeInvisibleTypeAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
