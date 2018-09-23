package dataobject;

import adt.u1;
import adt.u2;
import classfile.constant.ConstantPoolTags;
import classfile.constantpool.AbstractConstantPool;
import classfile.factory.ConstantPoolSFactory;
import exception.ClassLoadingException;
import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;

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
