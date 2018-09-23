package parser;

public class Readability {
    public static String getFieldAccessFlagString(int acc) {
        StringBuilder sb = new StringBuilder();
        if ((acc & 0x0001) != 0) {
            sb.append("public ");
        }
        if ((acc & 0x0002) != 0) {
            sb.append("private ");
        }
        if ((acc & 0x0004) != 0) {
            sb.append("protected ");
        }
        if ((acc & 0x0008) != 0) {
            sb.append("static ");
        }
        if ((acc & 0x0010) != 0) {
            sb.append("final ");
        }
        if ((acc & 0x0040) != 0) {
            sb.append("volatile ");
        }
        if ((acc & 0x0080) != 0) {
            sb.append("transient ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("synthetic ");
        }
        if ((acc & 0x4000) != 0) {
            sb.append("enum ");
        }
        return sb.toString();
    }

    public static String getMethodAccessFlagString(int acc) {
        StringBuilder sb = new StringBuilder();
        if ((acc & 0x0001) != 0) {
            sb.append("public ");
        }
        if ((acc & 0x0002) != 0) {
            sb.append("private ");
        }
        if ((acc & 0x0004) != 0) {
            sb.append("protected ");
        }
        if ((acc & 0x0008) != 0) {
            sb.append("static ");
        }
        if ((acc & 0x0010) != 0) {
            sb.append("final ");
        }
        if ((acc & 0x0020) != 0) {
            sb.append("synchronized ");
        }
        if ((acc & 0x0040) != 0) {
            sb.append("bridge ");
        }
        if ((acc & 0x0080) != 0) {
            sb.append("varargs ");
        }
        if ((acc & 0x0100) != 0) {
            sb.append("native ");
        }
        if ((acc & 0x0400) != 0) {
            sb.append("abstract ");
        }
        if ((acc & 0x0800) != 0) {
            sb.append("strict ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("synthetic ");
        }
        return sb.toString();
    }

    public static String getClassAccessFlagString(int acc) {
        StringBuilder sb = new StringBuilder();

        if ((acc & 0x0001) != 0) {
            sb.append("ACC_PUBLIC ");
        }
        if ((acc & 0x0010) != 0) {
            sb.append("ACC_FINAL ");
        }
        if ((acc & 0x0020) != 0) {
            sb.append("ACC_SUPER ");
        }
        if ((acc & 0x0200) != 0) {
            sb.append("ACC_INTERFACE ");
        }
        if ((acc & 0x0400) != 0) {
            sb.append("ACC_ABSTRACT ");
        }
        if ((acc & 0x1000) != 0) {
            sb.append("ACC_SYNTHETIC ");
        }
        if ((acc & 0x2000) != 0) {
            sb.append("ACC_ANNOTATION ");
        }
        if ((acc & 0x4000) != 0) {
            sb.append("ACC_ENUM ");
        }
        return sb.toString();
    }

    public static String peelFieldDescriptor(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            switch (str.charAt(i)) {
                case 'B':
                    sb.append("byte");
                    break;
                case 'C':
                    sb.append("char");
                    break;
                case 'D':
                    sb.append("double");
                    break;
                case 'F':
                    sb.append("float");
                    break;
                case 'I':
                    sb.append("int");
                    break;
                case 'J':
                    sb.append("long");
                    break;
                case 'L':
                    i++;
                    StringBuilder slashClassName = new StringBuilder();
                    while (str.charAt(i) != ';') {
                        slashClassName.append(str.charAt(i));
                        i++;
                    }
                    sb.append(slashClassName.toString().replaceAll("/", "."));
                    break;
                case '[':
                    i++;
                    int dimensionCount = 1;
                    while (str.charAt(i) == '[') {
                        dimensionCount++;
                        i++;
                    }
                    StringBuilder component = new StringBuilder();
                    switch (str.charAt(i)) {
                        case 'B':
                            sb.append("byte");
                            break;
                        case 'C':
                            sb.append("char");
                            break;
                        case 'D':
                            sb.append("double");
                            break;
                        case 'F':
                            sb.append("float");
                            break;
                        case 'I':
                            sb.append("int");
                            break;
                        case 'J':
                            sb.append("long");
                            break;
                        case 'L':
                            i++;
                            StringBuilder slashClassName2 = new StringBuilder();
                            while (str.charAt(i) != ';') {
                                slashClassName2.append(str.charAt(i));
                                i++;
                            }
                            component = new StringBuilder(slashClassName2.toString().replaceAll("/", "."));
                            break;
                    }
                    for (int p = 0; p < dimensionCount; p++) {
                        component.append("[]");
                    }
                    sb.append(component);
                    break;

            }
            i++;
        }
        return sb.toString();
    }
}