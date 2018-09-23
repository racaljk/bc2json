package classfile.field;

import adt.u1;
import adt.u2;
import adt.u4;
import classfile.attribute.Attribute;
import classfile.attribute.CodeAttribute;
import classfile.constantpool.AbstractConstantPool;
import classfile.constantpool.ConstantUtf8Info;
import classfile.factory.AttributeFactory;
import dataobject.ConstantPoolObject;
import exception.ClassLoadingException;
import parser.BytesReaderProxy;
import parser.ClassFileReader;
import parser.Stuffable;

import java.io.IOException;

public class FieldInfo extends BytesReaderProxy implements Stuffable {
    public u2 nameIndex;
    public u2 descriptorIndex;
    public u2 attributeCount;
    public Attribute[] attributes;
    public u2 accessFlags;
    @SuppressWarnings("unchecked")
    private u1[] customeAttribute;
    private ConstantPoolObject constantPoolObject;

    public FieldInfo(ClassFileReader reader, ConstantPoolObject constantPoolObject) {
        super(reader);
        this.constantPoolObject = constantPoolObject;
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        accessFlags = read2Bytes();
        nameIndex = read2Bytes();

        descriptorIndex = read2Bytes();

        attributeCount = read2Bytes();

        attributes = new Attribute[attributeCount.getValue()];

        for (int i = 0; i < attributeCount.getValue(); i++) {
            u2 attributeNameIndex = read2Bytes();
            AbstractConstantPool cb = constantPoolObject.at(attributeNameIndex.getValue());

            //if it's not a classfile.constant utf-8 info structure then throws an exception
            if (cb instanceof ConstantUtf8Info) {
                Attribute attr = AttributeFactory.create(getReader(), cb.toString());

                //special method invoking
                if (attr instanceof CodeAttribute) {
                    ((CodeAttribute) attr).linkToConstantPool(constantPoolObject);
                }

                if (attr != null) {
                    attr.stuffing();
                    attributes[i] = attr;
                } else {
                    u4 customAttributeLength = read4Bytes();
                    customeAttribute = new u1[(int) customAttributeLength.getValue()];

                    for (int m = 0; m < customeAttribute.length; m++) {
                        customeAttribute[m] = read1Byte();
                    }
                }
            } else {
                throw new ClassLoadingException(nameIndex.getValue() + "," + descriptorIndex.getValue() + "," + attributeCount.getValue() + "," + attributeNameIndex.getValue() + "class load exception");
            }
        }
    }
}
