import b2j.B2Json;
import b2j.Option;

public class Launcher {
    public static void main(String[] args) {
        B2Json b2Json = B2Json.fromFilePath("C:\\Users\\Cthulhu\\Desktop\\bc2json\\src\\test\\java\\Test.class");
        b2Json.withOption(Option.PRETTY_PRINTING);
        //b2Json.withOption(Option.MORE_READABLE);
        System.out.println(b2Json.toJsonString());
    }
}
