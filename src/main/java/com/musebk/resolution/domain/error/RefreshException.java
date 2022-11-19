package com.musebk.resolution.domain.error;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 15:50
 * @Since
 */
public class RefreshException extends RuntimeException {
    public static final RefreshException DEFAULT_SINGLE = new RefreshException();

    public RefreshException() {
        super("抱歉框架调用刷新时异常");
    }

    public RefreshException(Throwable cause) {
        super(cause);
    }

    public RefreshException(String message, Throwable cause) {
        super(message, cause);
    }
    public RefreshException(String message) {
        super(message);
    }

}
