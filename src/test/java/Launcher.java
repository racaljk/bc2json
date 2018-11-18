import b2j.B2Json;
import b2j.OptionConst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> bytecodeFileNames = getFile(".\\src\\test\\java\\java\\io\\DataInputStream.class");
        for (String bytecodeFileName : bytecodeFileNames) {
            System.out.println("=====" + bytecodeFileName);
            B2Json b2Json = B2Json.fromInputStream(new FileInputStream(new File(bytecodeFileName)));
            b2Json.withOption(OptionConst.PRETTY_PRINTING);
            b2Json.withOption(OptionConst.MORE_READABLE);
            System.out.println(b2Json.toJsonString());
        }
    }

    @SuppressWarnings("unused")
    private static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        assert tempList != null;
        for (File entity : tempList) {
            if (entity.isFile()) {
                files.add(entity.toString());
            }
            if (entity.isDirectory()) {
                files.addAll(getFiles(entity.getPath()));
            }
        }
        return files;
    }

    @SuppressWarnings("unused")
    private static ArrayList<String> getFile(String path) {
        ArrayList<String> file = new ArrayList<>();
        File f = new File(path);
        file.add(f.toString());
        return file;
    }
}
