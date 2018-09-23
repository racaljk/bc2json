package classfile.attribute;

import parser.ClassFileReader;

public class RuntimeInvisibleTypeAnnotationsAttribute extends RuntimeVisibleTypeAnnotationsAttribute {

    public RuntimeInvisibleTypeAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }
}
