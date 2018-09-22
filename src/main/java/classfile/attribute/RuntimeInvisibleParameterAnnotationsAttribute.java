package classfile.attribute;

import bcm.ClassFileReader;

public class RuntimeInvisibleParameterAnnotationsAttribute extends RuntimeVisibleParameterAnnotationsAttribute {
    public RuntimeInvisibleParameterAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
