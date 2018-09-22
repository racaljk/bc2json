import adt.u1;
import adt.u2;
import adt.u4;
import bcm.B2JClassLoader;
import bcm.B2JRawClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataobject.ConstantPoolObject;
import dataobject.FieldObject;
import dataobject.InterfacesObject;
import dataobject.MethodObject;
import exception.ClassLoadingException;
import serialize.*;

import java.io.FileNotFoundException;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException, ClassLoadingException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(u2.class, new u2Serializer());
        builder.registerTypeAdapter(u1.class, new u1Serializer());
        builder.registerTypeAdapter(u4.class, new u4Serializer());
        builder.registerTypeAdapter(ConstantPoolObject.class, new ConstantPoolObjectSerializer());
        builder.registerTypeAdapter(FieldObject.class, new FieldObjectSerializer());
        builder.registerTypeAdapter(InterfacesObject.class, new InterfacesObjectSerializer());
        builder.registerTypeAdapter(MethodObject.class, new MethodObjectSerializer());

        Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();

        B2JClassLoader cl = new B2JClassLoader("C:\\Users\\Cthulhu\\Desktop\\bc2json\\src\\test\\java\\Example.class");
        B2JRawClass result = cl.parseClass();
        System.out.println(gson.toJson(result));
    }
}
