import b2j.B2Json;
import b2j.OptionConst;

import java.io.File;
import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) {
        ArrayList<String> bytecodeFiles = getFiles("./src/test/java/java");
        for (String bytecodeFile : bytecodeFiles) {
            System.out.println("=====" + bytecodeFile);
            B2Json b2Json = B2Json.fromFilePath(bytecodeFile);
            b2Json.withOption(OptionConst.PRETTY_PRINTING);
            b2Json.withOption(OptionConst.MORE_READABLE);
            System.out.println(b2Json.toJsonString());
        }
    }

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
}
