package parser;

import dataobject.*;
import parser.classfile.adt.u2;

public class B2JRawClass {
    @SuppressWarnings("unused")
    public int magic = 0xcafebabe;
    public u2 minor_version;
    public u2 major_version;
    public ConstantPoolObject pool_slots;
    public u2 access_flag;
    public u2 this_class;
    public u2 super_class;
    public InterfacesObject interfaces;
    public FieldObject fields;
    public MethodObject methods;
    public ClassFileAttributeObject classfile_attributes;
}
