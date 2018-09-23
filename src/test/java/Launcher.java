import b2j.B2Json;
import b2j.OptionConst;

public class Launcher {
    public static void main(String[] args) {
        B2Json b2Json = B2Json.fromFilePath("C:\\Users\\Cthulhu\\Desktop\\bc2json\\src\\test\\java\\Example2.class");
        b2Json.withOption(OptionConst.PRETTY_PRINTING);
        b2Json.withOption(OptionConst.MORE_READABLE);
        System.out.println(b2Json.toJsonString());
    }
}
