package com.musebk.resolution.domain.error;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 22:46
 * @Since
 */
public class NoNetworkCardException extends RuntimeException {
    public static final NoNetworkCardException DEFAULT_SINGLE = new NoNetworkCardException();

    public NoNetworkCardException() {
        super("未获取到任何网卡信息");
    }

    public NoNetworkCardException(Throwable cause) {
        super(cause);
    }

    public NoNetworkCardException(String message) {
        super(message);
    }

    public NoNetworkCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
