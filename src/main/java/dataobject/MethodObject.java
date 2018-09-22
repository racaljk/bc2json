package dataobject;

import bcm.BytesReaderProxy;
import bcm.ClassFileReader;
import bcm.Stuffable;
import classfile.field.FieldInfo;
import com.google.gson.annotations.Expose;
import exception.ClassLoadingException;

import java.io.IOException;
import java.util.ArrayList;

public class MethodObject extends BytesReaderProxy implements Stuffable {
    @Expose
    private int methodCount;
    @Expose
    private ArrayList<FieldInfo> methods;
    private ConstantPoolObject cp;

    public MethodObject(ClassFileReader reader, ConstantPoolObject cpObject, int methodCount) {
        super(reader);
        this.methodCount = methodCount;
        this.cp = cpObject;
        methods = new ArrayList<>();
    }

    @Override
    public void stuffing() throws IOException, ClassLoadingException {
        for (int i = 0; i < methodCount; i++) {
            FieldInfo field = new FieldInfo(getReader(), cp);
            field.stuffing();
            methods.add(field);
        }
    }
}