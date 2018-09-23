package classfile.constantpool;

import classfile.adt.u1;
import classfile.adt.u2;
import classfile.constant.ConstantPoolTags;
import parser.ClassFileReader;

import java.io.IOException;

public class ConstantUtf8Info extends AbstractConstantPool {
    public static final u1 tag = new u1(ConstantPoolTags.CONSTANT_Utf8);
    private u2 length;
    private u1[] bytes;

    public ConstantUtf8Info(ClassFileReader reader) {
        super(reader);
    }

    @Override
    public void stuffing() throws IOException {
        length = read2Bytes();
        bytes = new u1[length.getValue()];
        for (int i = 0; i < length.getValue(); i++) {
            bytes[i] = read1Byte();
        }
    }

    @Override
    public String toString() {
        byte[] bs = new byte[length.getValue()];
        for (int i = 0; i < length.getValue(); i++) {
            bs[i] = (byte) bytes[i].getValue();
        }

        return new String(bs);
    }
}
