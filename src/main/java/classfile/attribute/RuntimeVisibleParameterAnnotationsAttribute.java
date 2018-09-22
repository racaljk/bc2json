package classfile.attribute;

import adt.u1;
import adt.u2;
import bcm.ClassFileReader;
import bcm.Stuffable;
import exception.ClassLoadingException;

import java.io.IOException;

public class RuntimeVisibleParameterAnnotationsAttribute extends Attribute {
    private u1 numParameters;
    private ParameterAnnotation[] parameterAnnotations;

    public RuntimeVisibleParameterAnnotationsAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        attributeLength = read4Bytes();
        numParameters = read1Byte();
        parameterAnnotations = new ParameterAnnotation[numParameters.getValue()];
        for (int i = 0; i < numParameters.getValue(); i++) {
            ParameterAnnotation pa = new ParameterAnnotation();
            pa.stuffing();
            parameterAnnotations[i] = pa;
        }
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        int res = 1;
        for (ParameterAnnotation pa : parameterAnnotations) {
            res += pa.getActualBytes();
        }
        return res;
    }

    class ParameterAnnotation implements Stuffable, Verifiable {
        u2 numAnnotations;
        Annotation[] annotations;

        @Override
        public void stuffing() throws IOException, ClassLoadingException {
            numAnnotations = read2Bytes();
            annotations = new Annotation[numAnnotations.getValue()];
            for (int i = 0; i < numAnnotations.getValue(); i++) {
                Annotation x = new Annotation(getReader());
                x.stuffing();
                annotations[i] = x;
            }
        }

        @Override
        public int getActualBytes() {
            int res = 2;
            for (Annotation a : annotations) {
                res += a.getActualBytes();
            }
            return res;
        }
    }
}
