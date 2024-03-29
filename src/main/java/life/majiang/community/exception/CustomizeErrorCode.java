package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你的问题找不到了,请换一个吧！",2001),
    TARGET_NOT_FOUND("未选中任何问题或评论进行回复.",2002),
    NO_LOGIN("当前操作需要登录，请登录重试。.",2003),
    SYS_ERROR("服务器冒烟了！！,请稍后访问！",2004),
    TYPE_PARAM_WRONG("评论类型错误或不存在！",2005),
    COMMENT_NO_FOUND("你回复的评论不存在",2006),
    COMMENT_IS_EMPTY("输入内容不能为空！",2007),
    READ_NOTIFICATION_FALL("兄弟你这是读别人的信息呢？",2008),
    NOTIFICATION_NOT_FOUND("消息莫非是不是不翼而飞？",2009),
    FILE_UPLOAD_FALL("图片上传失败！",2010)
    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(String message,Integer code) {
        this.message=message;
        this.code=code;

    }

    CustomizeErrorCode(String message) {
        this.message=message;
    }
    CustomizeErrorCode(Integer code) {
        this.code=code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
