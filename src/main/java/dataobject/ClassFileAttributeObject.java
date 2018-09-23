package dataobject;

import adt.u1;
import adt.u2;
import adt.u4;
import classfile.attribute.Attribute;
import classfile.attribute.CodeAttribute;
import classfile.attribute.SourceFileAttribute;
import classfile.constantpool.AbstractConstantPool;
import classfile.constantpool.ConstantUtf8Info;
import classfile.factory.AttributeFactory;
import exception.ClassLoadingException;
import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;

import java.io.IOException;
import java.util.ArrayList;

public class ClassFileAttributeObject extends BytesReaderProxy implements Stuffable {
    private int attributeCount;
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
            //if it's not a classfile.constant utf-8 info structure then throws an exception
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
                throw new RuntimeException("class load exception");
            }
        }
    }

    public ArrayList<ArrayList<String>> toStringMatrix() {
        ArrayList<ArrayList<String>> res = new ArrayList<>();

        for (Attribute attr : attributes) {
            ArrayList<String> arr = new ArrayList<>();
            if (attr instanceof SourceFileAttribute) {
                arr.add("SourceFileAttribute");
                arr.add(cp.at(((SourceFileAttribute) attr).getSourceFileIndex().getValue()).toString());
            }
            res.add(arr);
        }
        return res;
    }
}
