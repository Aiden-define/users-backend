package ctgu.yao.usersbackend.utils;

public enum ErrorCode {

    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求参数为空",""),
    NOT_LOGIN(40002,"未登录",""),
    NO_AUTH(40002,"无权限",""),
    SYSTEM_ERROR(50000,"系统错误","");


    private final int code;
    /**
     * 状态码描述
     */
    private final String message;
    /**
     * 状态码描述（详细）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
