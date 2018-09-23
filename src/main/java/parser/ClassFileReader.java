package parser;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.adt.u4;

import java.io.*;

@SuppressWarnings("all")
public class ClassFileReader {
    private DataInputStream in;

    public ClassFileReader(String javaClassPath) throws FileNotFoundException {
        File f = new File(javaClassPath);
        if (f.exists()) {
            in = new DataInputStream(new FileInputStream(f));
        } else {
            throw new FileNotFoundException("Can not find " + javaClassPath);
        }
    }

    public boolean isEOF() throws IOException {
        return in.available() == 0;
    }

    public u1 read1Byte() throws IOException {
        int a = in.readUnsignedByte();
        return new u1(a);
    }

    public u2 read2Bytes() throws IOException {
        int a = in.readUnsignedShort();
        return new u2(a);
    }

    public u4 read4Bytes() throws IOException {
        int a = in.readInt();
        return new u4(a);
    }
}

