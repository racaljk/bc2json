package classfile.attribute;

import parser.ClassFileReader;

public class RuntimeInvisibleAnnotationsAttribute extends RuntimeVisibleAnnotationsAttribute {
    public RuntimeInvisibleAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
