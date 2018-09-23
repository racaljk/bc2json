package classfile.attribute.typeannotation;

import parser.ClassFileReader;

import java.io.IOException;

public class EmptyTarget extends Target {
    public EmptyTarget(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        //do nothing
    }

    @Override
    public int getActualBytes() {
        return 0;
    }
}
