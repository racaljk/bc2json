package b2j;

public class Option {
    private boolean moreReadable = false;
    private boolean ignoreClassFileAttribute = false;
    private boolean ignoreMethods = false;
    private boolean ignoreFields = false;
    private boolean ignoreInterfaces = false;
    private boolean ignoreConstantPool = false;

    public boolean isMoreReadable() {
        return moreReadable;
    }

    public void setMoreReadable(boolean moreReadable) {
        this.moreReadable = moreReadable;
    }

    public boolean isIgnoreClassFileAttribute() {
        return ignoreClassFileAttribute;
    }

    public void setIgnoreClassFileAttribute(boolean ignoreClassFileAttribute) {
        this.ignoreClassFileAttribute = ignoreClassFileAttribute;
    }

    public boolean isIgnoreMethods() {
        return ignoreMethods;
    }

    public void setIgnoreMethods(boolean ignoreMethods) {
        this.ignoreMethods = ignoreMethods;
    }

    public boolean isIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(boolean ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    public boolean isIgnoreInterfaces() {
        return ignoreInterfaces;
    }

    public void setIgnoreInterfaces(boolean ignoreInterfaces) {
        this.ignoreInterfaces = ignoreInterfaces;
    }

    public boolean isIgnoreConstantPool() {
        return ignoreConstantPool;
    }

    public void setIgnoreConstantPool(boolean ignoreConstantPool) {
        this.ignoreConstantPool = ignoreConstantPool;
    }
}
