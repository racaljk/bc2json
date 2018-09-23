package parser;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.adt.u4;

import java.io.IOException;

public class BytesReaderProxy {
    private ClassFileReader reader;

    protected BytesReaderProxy(ClassFileReader reader) {
        this.reader = reader;
    }

    protected final ClassFileReader getReader() {
        return reader;
    }

    protected final u1 read1Byte() throws IOException {
        return reader.read1Byte();
    }

    protected final u2 read2Bytes() throws IOException {
        return reader.read2Bytes();
    }

    protected final u4 read4Bytes() throws IOException {
        return reader.read4Bytes();
    }
}
