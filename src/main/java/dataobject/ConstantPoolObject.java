package dataobject;

import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.constant.ConstantPoolTags;
import parser.classfile.constantpool.AbstractConstantPool;
import parser.classfile.exception.ClassLoadingException;
import parser.classfile.factory.ConstantPoolSFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstantPoolObject extends BytesReaderProxy implements Stuffable {
    private ArrayList<AbstractConstantPool> poolSlots = new ArrayList<>();
    private u2 slotsNum;

    public ConstantPoolObject(ClassFileReader reader, u2 slotsNum) {
        super(reader);
        this.slotsNum = slotsNum;
    }

    public u2 getSlotsNum() {
        return slotsNum;
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        for (int i = 1; i <= slotsNum.getValue() - 1; i++) {
            u1 tag = read1Byte();
            AbstractConstantPool info = ConstantPoolSFactory.create(getReader(), tag.getValue());
            info.setTableIndex(i);
            info.stuffing();
            poolSlots.add(info);

            if (tag.getValue() == ConstantPoolTags.CONSTANT_Long || tag.getValue() == ConstantPoolTags.CONSTANT_Double) {
                i++;
            }
        }
    }

    public AbstractConstantPool at(int index) {
        for (AbstractConstantPool cb : poolSlots) {
            if (index == cb.getTableIndex()) {
                return cb;
            }
        }
        return null;
    }

    public List<AbstractConstantPool> getSlots() {
        return poolSlots;
    }
}
