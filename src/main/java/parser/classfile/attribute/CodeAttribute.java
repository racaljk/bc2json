package parser.classfile.attribute;

import dataobject.ConstantPoolObject;
import parser.ClassFileReader;
import parser.classfile.adt.u1;
import parser.classfile.adt.u2;
import parser.classfile.adt.u4;
import parser.classfile.constantpool.AbstractConstantPool;
import parser.classfile.constantpool.ConstantUtf8Info;
import parser.classfile.exception.ClassLoadingException;
import parser.classfile.factory.AttributeFactory;

import java.io.IOException;

public class CodeAttribute extends Attribute {
    public u2 maxStack;
    public u2 maxLocals;
    public u1[] code;
    public u2 exceptionTableLength;
    public ExceptionTable[] table;
    private u4 codeLength;
    private u2 attributesCount;
    private Attribute[] attributes;
    private u1[] customeAttribute;
    private ConstantPoolObject constantPoolObject;

    public CodeAttribute(ClassFileReader reader) {
        super(reader);
    }

    /**
     * this is a special method in all attributes class
     * code parser.classfile.attribute may has other parser.classfile.attribute, so we must
     * to check if it's a valid parser.classfile.attribute before ready next
     * bytes;
     *
     * @param constantPoolObject
     */
    public void linkToConstantPool(ConstantPoolObject constantPoolObject) {
        this.constantPoolObject = constantPoolObject;
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        attributeLength = read4Bytes();
        maxStack = read2Bytes();
        maxLocals = read2Bytes();

        codeLength = read4Bytes();
        if (codeLength.getValue() < 0 || codeLength.getValue() > 65536) {
            throw new ClassLoadingException("code length out of limitation");
        }
        code = new u1[(int) codeLength.getValue()];
        for (int i = 0; i < codeLength.getValue(); i++) {
            code[i] = read1Byte();
        }

        exceptionTableLength = read2Bytes();
        table = new ExceptionTable[exceptionTableLength.getValue()];
        for (int k = 0; k < exceptionTableLength.getValue(); k++) {
            ExceptionTable et = new ExceptionTable();
            et.stuffing();
            table[k] = et;
        }
        attributesCount = read2Bytes();

        //extra check
        if (attributesCount.getValue() > 0 && constantPoolObject == null) {
            throw new RuntimeException("no parser.classfile.constant pool linked");
        }

        attributes = new Attribute[attributesCount.getValue()];

        for (int t = 0; t < attributesCount.getValue(); t++) {
            u2 attributeNameIndex = read2Bytes();
            AbstractConstantPool cb = constantPoolObject.at(attributeNameIndex.getValue());
            //if it's not a parser.classfile.constant utf-8 info structure then throws an parser.classfile.exception

            if (cb instanceof ConstantUtf8Info) {
                Attribute codeAttr = AttributeFactory.create(getReader(), cb.toString());

                //special method invoking
                if (codeAttr instanceof CodeAttribute) {
                    ((CodeAttribute) codeAttr).linkToConstantPool(constantPoolObject);
                }

                if (codeAttr != null) {
                    codeAttr.stuffing();
                    attributes[t] = codeAttr;
                } else {
                    u4 customAttributeLength = read4Bytes();
                    customeAttribute = new u1[(int) customAttributeLength.getValue()];
                    for (int m = 0; m < customeAttribute.length; m++) {
                        customeAttribute[m] = read1Byte();
                    }
                }
            } else {
                throw new ClassLoadingException(t + "," + "class load parser.classfile.exception");
            }
        }
    }

    @Override
    public int getActualBytes() {
        int res = 8;
        res += code.length;
        res += 2;
        for (ExceptionTable et : table) {
            res += et.getActualBytes();
        }
        res += 2;
        if (customeAttribute == null || customeAttribute.length == 0) {
            for (Attribute ab : attributes) {
                res += ab.getActualBytes();
                //it's important
                //other parser.classfile.attribute many existed in code parser.classfile.attribute structure
                //so we need to add extra 6 bytes representing attributeNameIndex
                //attributeLength
                res += 6;
            }
        } else {
            res += customeAttribute.length;
        }
        return res;
    }

    public class ExceptionTable {
        public u2 startPc;
        public u2 endPc;
        public u2 handlerPc;
        public u2 catchType;

        void stuffing() throws IOException {
            startPc = read2Bytes();
            endPc = read2Bytes();
            handlerPc = read2Bytes();
            catchType = read2Bytes();
        }

        int getActualBytes() {
            return 8;
        }
    }
}
