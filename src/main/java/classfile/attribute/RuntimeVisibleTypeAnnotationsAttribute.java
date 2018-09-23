package classfile.attribute;

import adt.u2;
import classfile.exception.ClassLoadingException;
import parser.ClassFileReader;

import java.io.IOException;

public class RuntimeVisibleTypeAnnotationsAttribute extends Attribute {
    private u2 numAnnotations;
    private TypeAnnotation[] typeAnnotations;

    public RuntimeVisibleTypeAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        attributeLength = read4Bytes();
        numAnnotations = read2Bytes();
        typeAnnotations = new TypeAnnotation[numAnnotations.getValue()];
        for (int i = 0; i < numAnnotations.getValue(); i++) {
            TypeAnnotation ta = new TypeAnnotation(getReader());
            ta.stuffing();
            typeAnnotations[i] = ta;
        }
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        int res = 2;
        for (TypeAnnotation ta : typeAnnotations) {
            res += ta.getActualBytes();
        }
        return res;
    }
}
