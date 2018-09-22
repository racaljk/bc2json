package classfile.constantpool;

import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;


abstract public class AbstractConstantPool extends BytesReaderProxy implements Stuffable {
    private int tableIndex;

    AbstractConstantPool(ClassFileReader reader) {
        super(reader);
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int index) {
        tableIndex = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
