package b2j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataobject.*;
import exception.ClassLoadingException;
import parser.B2JClassLoader;
import parser.B2JRawClass;
import serialize.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class B2Json {
    private static GsonBuilder builder = new GsonBuilder();

    static {
        builder.registerTypeAdapter(ConstantPoolObject.class, new ConstantPoolObjectSerializer());
        builder.registerTypeAdapter(FieldObject.class, new FieldObjectSerializer());
        builder.registerTypeAdapter(InterfacesObject.class, new InterfacesObjectSerializer());
        builder.registerTypeAdapter(MethodObject.class, new MethodObjectSerializer());
        builder.registerTypeAdapter(ClassFileAttributeObject.class, new ClassFileAttributeObjectSerializer());
    }

    private B2JRawClass raw;
    private boolean moreReadable = false;

    private B2Json() {
    }

    public static B2Json fromFilePath(String path) {
        B2Json b2Json = null;

        try {
            b2Json = new B2Json();
            b2Json.raw = new B2JClassLoader(path).parseClass();
        } catch (ClassLoadingException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return b2Json;
    }

    public B2Json withOption(Option opt) {
        switch (opt) {
            case PRETTY_PRINTING:
                builder.setPrettyPrinting();
            case MORE_READABLE:
                moreReadable = true;
                break;
        }
        return this;
    }

    public String toJsonString() {
        builder.registerTypeAdapter(B2JRawClass.class, new B2JRawClassSerializer(moreReadable));
        Gson gson = builder.create();
        return gson.toJson(raw);
    }

    public void toJsonFile(String fileName) {
        builder.registerTypeAdapter(B2JRawClass.class, new B2JRawClassSerializer(moreReadable));
        File file = new File(fileName);
        Gson gson = builder.create();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(gson.toJson(raw).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
