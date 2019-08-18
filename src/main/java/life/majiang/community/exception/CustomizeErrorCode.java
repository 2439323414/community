package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你的问题找不到了,请换一个吧！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message=message;

    }

    @Override
    public String getMessage() {
        return message;
    }
}
