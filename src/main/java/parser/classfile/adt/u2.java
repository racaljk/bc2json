package parser.classfile.adt;

import com.google.gson.annotations.Expose;

public class u2 {
    @Expose
    private int value;

    public u2(int v) {
        value = v;
    }

    public int getValue() {
        return value;
    }
}
