package dataobject;

import classfile.adt.u2;
import classfile.constantpool.ConstantClassInfo;
import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;

import java.io.IOException;


public class InterfacesObject extends BytesReaderProxy implements Stuffable {
    private int interfacesCount;
    private u2[] interfaces;
    private ConstantPoolObject cp;

    public InterfacesObject(ClassFileReader reader, ConstantPoolObject cp, int interfacesCount) {
        super(reader);
        this.cp = cp;
        this.interfacesCount = interfacesCount;
        interfaces = new u2[interfacesCount];
    }

    @Override
    public void stuffing() throws IOException {
        for (int i = 0; i < interfacesCount; i++) {
            u2 x = read2Bytes();
            interfaces[i] = x;
        }
    }

    public String[] toStringMatrix() {
        String[] inters = new String[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            int index = cp.at(interfaces[i].getValue()).getTableIndex();
            String name = cp.at(((ConstantClassInfo) cp.at(index)).nameIndex.getValue()).toString();
            inters[i] = name;
        }
        return inters;
    }
}
