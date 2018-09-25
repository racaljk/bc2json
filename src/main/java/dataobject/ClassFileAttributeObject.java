package dataobject;

import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.adt.u4;
import parser.classfile.attribute.Attribute;
import parser.classfile.attribute.CodeAttribute;
import parser.classfile.constantpool.AbstractConstantPool;
import parser.classfile.constantpool.ConstantUtf8Info;
import parser.classfile.exception.ClassLoadingException;
import parser.classfile.factory.AttributeFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ClassFileAttributeObject extends BytesReaderProxy implements Stuffable {
    private int attributeCount;

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ConstantPoolObject getCp() {
        return cp;
    }

    private ArrayList<Attribute> attributes;
    private u1[] customAttribute;
    private ConstantPoolObject cp;

    public ClassFileAttributeObject(ClassFileReader reader, ConstantPoolObject cpObject, int attributeCount) {
        super(reader);
        this.cp = cpObject;
        this.attributeCount = attributeCount;
        attributes = new ArrayList<>();
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        for (int i = 0; i < attributeCount; i++) {
            u2 attributeNameIndex = read2Bytes();

            //We need to check if it's a \"ConstantUtf8Info\" instance"
            AbstractConstantPool cb = cp.at(attributeNameIndex.getValue());
            //if it's not a parser.classfile.constant utf-8 info structure then throws an parser.classfile.exception
            if (cb instanceof ConstantUtf8Info) {
                Attribute attr = AttributeFactory.create(getReader(), cb.toString());

                //special method invoking
                if (attr instanceof CodeAttribute) {
                    ((CodeAttribute) attr).linkToConstantPool(cp);
                }

                if (attr != null) {
                    attr.stuffing();
                    attributes.add(attr);
                } else {
                    u4 customAttributeLength = read4Bytes();
                    customAttribute = new u1[(int) customAttributeLength.getValue()];
                    for (int m = 0; m < customAttribute.length; m++) {
                        customAttribute[m] = read1Byte();
                    }
                }
            } else {
                throw new RuntimeException("class load parser.classfile.exception");
            }
        }
    }
}
