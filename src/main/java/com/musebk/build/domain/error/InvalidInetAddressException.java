package com.musebk.build.domain.error;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 23:01
 * @Since
 */
public class InvalidInetAddressException  extends RuntimeException {

    public InvalidInetAddressException(Throwable cause) {
        super(cause);
    }

    public InvalidInetAddressException(String message) {
        super(message);
    }

    public InvalidInetAddressException(String message, Throwable cause) {
        super(message, cause);
    }
}
