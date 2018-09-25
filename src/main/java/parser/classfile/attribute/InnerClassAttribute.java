package parser.classfile.attribute;

import parser.ClassFileReader;
import parser.classfile.adt.u2;

import java.io.IOException;

public class InnerClassAttribute extends Attribute {
    public u2 numberOfClasses;
    public CPIndexBundle[] classes;

    public InnerClassAttribute(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        attributeLength = read4Bytes();
        numberOfClasses = read2Bytes();
        classes = new CPIndexBundle[numberOfClasses.getValue()];
        for (int i = 0; i < numberOfClasses.getValue(); i++) {
            CPIndexBundle bundle = new CPIndexBundle();
            bundle.stuffing();
            classes[i] = bundle;
        }
    }

    @Override
    @ExcludeFields
    public int getActualBytes() {
        int res = 2;
        for (CPIndexBundle cpb : classes) {
            res += cpb.getActualBytes();
        }
        return res;
    }

    public class CPIndexBundle {
        public u2 innerClassInfoIndex;
        public u2 outerClassInfoIndex;
        public u2 innerNameIndex;
        public u2 innerClassAccessFlags;

        void stuffing() throws IOException {
            innerClassInfoIndex = read2Bytes();
            outerClassInfoIndex = read2Bytes();
            innerNameIndex = read2Bytes();
            innerClassAccessFlags = read2Bytes();
        }

        int getActualBytes() {
            return 8;
        }
    }
}
