import b2j.B2Json;

public class Launcher {
    public static void main(String[] args) {
        B2Json b2Json = B2Json.fromFilePath("C:\\Users\\Cthulhu\\Desktop\\bc2json\\src\\test\\java\\Test.class");
        //b2Json.withOption(Option.PRETTY_PRINTING);

        System.out.println(b2Json.toJsonString());
    }
}
