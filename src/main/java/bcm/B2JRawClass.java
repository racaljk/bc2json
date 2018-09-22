package bcm;

import adt.u2;
import com.google.gson.annotations.Expose;
import dataobject.*;

public class B2JRawClass {
    @Expose
    @SuppressWarnings("unused")
    final String magic = "cafebabe";
    @Expose
    u2 minor_version;
    @Expose
    u2 major_version;
    @Expose
    ConstantPoolObject pool_slots;
    @Expose
    u2 access_flag;
    @Expose
    u2 this_class;
    @Expose
    u2 super_class;
    @Expose
    InterfacesObject interfaces;
    @Expose
    FieldObject fields;
    @Expose
    MethodObject methods;
    @Expose
    ClassFileAttributeObject attrs;
}