package classfile.attribute;

import parser.ClassFileReader;

public class RuntimeInvisibleParameterAnnotationsAttribute extends RuntimeVisibleParameterAnnotationsAttribute {
    public RuntimeInvisibleParameterAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
