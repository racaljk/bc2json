package b2j;

import classfile.exception.ClassLoadingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataobject.*;
import parser.B2JClassLoader;
import parser.B2JRawClass;
import serialize.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class B2Json {
    private static GsonBuilder builder = new GsonBuilder();
    private B2JRawClass raw;
    private Option option = new Option();

    private B2Json() {
    }

    /**
     * Create a B2Json object for further use. This is the only way to create an instance of it since
     * B2json constructor method was private;
     *
     * @param path bytecode class file path
     * @return A B2Json object
     */
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

    private void registerSerializer() {
        builder.registerTypeAdapter(B2JRawClass.class, new B2JRawClassSerializer(option));
        builder.registerTypeAdapter(ConstantPoolObject.class, new ConstantPoolObjectSerializer(option));
        builder.registerTypeAdapter(FieldObject.class, new FieldObjectSerializer(option));
        builder.registerTypeAdapter(InterfacesObject.class, new InterfacesObjectSerializer());
        builder.registerTypeAdapter(MethodObject.class, new MethodObjectSerializer(option));
        builder.registerTypeAdapter(ClassFileAttributeObject.class, new ClassFileAttributeObjectSerializer());
    }

    /**
     * Add additional options to control the behaviors of json creation. Its supports the following options:
     *
     * <ol>
     * <li>OptionConst.PRETTY_PRINTING More pretty printing, it's especially useful when you works on developing phase</li>
     * <li>OptionConst.MORE_READABLE Make json string more readable. It's also recommend to set it.</li>
     * <li>OptionConst.IGNORE_CLASS_FILE_ATTRIBUTES Ignore the attributes of class file</li>
     * <li>OptionConst.IGNORE_METHODS Ignore methods</li>
     * <li>OptionConst.IGNORE_FIELDS Ignore fields</li>
     * <li>OptionConst.IGNORE_INTERFACES Ignore interfaces</li>
     * <li>OptionConst.IGNORE_CONSTANT_POOL Ignore constant pool slots</li>
     * </ol>
     *
     * @param opt option
     * @return A B2Json object
     */
    public B2Json withOption(OptionConst opt) {
        switch (opt) {
            case PRETTY_PRINTING:
                builder.setPrettyPrinting();
                break;
            case MORE_READABLE:
                option.setMoreReadable(true);
                break;
            case IGNORE_CLASS_FILE_ATTRIBUTES:
                option.setIgnoreClassFileAttribute(true);
                break;
            case IGNORE_FIELDS:
                option.setIgnoreFields(true);
                break;
            case IGNORE_METHODS:
                option.setIgnoreMethods(true);
                break;
            case IGNORE_INTERFACES:
                option.setIgnoreInterfaces(true);
                break;
            case IGNORE_CONSTANT_POOL:
                option.setIgnoreConstantPool(true);
                break;
        }
        return this;
    }

    /**
     * Output json as string
     *
     * @return string representation of json
     */
    public String toJsonString() {
        registerSerializer();
        Gson gson = builder.create();
        return gson.toJson(raw);
    }

    /**
     * Output json as a file
     * @param fileName file name
     */
    public void toJsonFile(String fileName) {
        registerSerializer();
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
