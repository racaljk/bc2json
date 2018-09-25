package serializer;

public class CoreMethodProperties {
    private String methodName;
    private String methodDescriptor;
    private String accessFlag;
    private String code;
    private int maxStack;
    private int maxLocal;
    private int knownExceptionNum;
    private KnownException[] exceptions;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDescriptor() {
        return methodDescriptor;
    }

    public void setMethodDescriptor(String methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    public String getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(String accessFlag) {
        this.accessFlag = accessFlag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxLocal() {
        return maxLocal;
    }

    public void setMaxLocal(int maxLocal) {
        this.maxLocal = maxLocal;
    }

    public int getKnownExceptionNum() {
        return knownExceptionNum;
    }

    public void setKnownExceptionNum(int knownExceptionNum) {
        this.knownExceptionNum = knownExceptionNum;
    }

    public KnownException[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(KnownException[] exceptions) {
        this.exceptions = exceptions;
    }

    public static class KnownException {
        int startPc;
        int endPc;
        int handlerPc;
        int catchType;

        public KnownException() {
        }

        public int getStartPc() {
            return startPc;
        }

        public void setStartPc(int startPc) {
            this.startPc = startPc;
        }

        public int getEndPc() {
            return endPc;
        }

        public void setEndPc(int endPc) {
            this.endPc = endPc;
        }

        public int getHandlerPc() {
            return handlerPc;
        }

        public void setHandlerPc(int handlerPc) {
            this.handlerPc = handlerPc;
        }

        public int getCatchType() {
            return catchType;
        }

        public void setCatchType(int catchType) {
            this.catchType = catchType;
        }
    }
}
