package classfile.attribute;

import bcm.ClassFileReader;

public class RuntimeInvisibleAnnotationsAttribute extends RuntimeVisibleAnnotationsAttribute {
    public RuntimeInvisibleAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
