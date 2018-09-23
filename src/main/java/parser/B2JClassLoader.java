package parser;

import adt.u2;
import adt.u4;
import dataobject.*;
import exception.ClassLoadingException;

import java.io.FileNotFoundException;
import java.io.IOException;


public class B2JClassLoader {
    private ClassFileReader reader;

    public B2JClassLoader(String javaClassPath) throws FileNotFoundException {
        reader = new ClassFileReader(javaClassPath);
    }

    public B2JRawClass parseClass() throws ClassLoadingException {
        B2JRawClass raw = new B2JRawClass();
        try {
            u4 magic = read4Bytes();
            if (magic.getValue() != 0xcafebabe) {
                throw new ClassLoadingException("it's not a java .class file");
            }
            raw.minor_version = read2Bytes();
            raw.major_version = read2Bytes();

            u2 constantPoolCount = read2Bytes();
            raw.pool_slots = new ConstantPoolObject(reader, constantPoolCount);
            raw.pool_slots.stuffing();

            raw.access_flag = read2Bytes();
            raw.this_class = read2Bytes();
            raw.super_class = read2Bytes();
            u2 interfaceCount = read2Bytes();

            raw.interfaces = new InterfacesObject(reader, raw.pool_slots, interfaceCount.getValue());
            raw.interfaces.stuffing();

            u2 fieldsCount = read2Bytes();
            raw.fields = new FieldObject(reader, raw.pool_slots, fieldsCount.getValue());
            raw.fields.stuffing();

            u2 methodsCount = read2Bytes();
            raw.methods = new MethodObject(reader, raw.pool_slots, methodsCount.getValue());
            raw.methods.stuffing();

            u2 classFileAttributeCount = read2Bytes();
            raw.classfile_attributes = new ClassFileAttributeObject(reader, raw.pool_slots, classFileAttributeCount.getValue());
            raw.classfile_attributes.stuffing();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raw;
    }

    private u2 read2Bytes() throws IOException {
        return reader.read2Bytes();
    }

    private u4 read4Bytes() throws IOException {
        return reader.read4Bytes();
    }
}
