package com.musebk.resolution.service;

import com.musebk.resolution.domain.DataError;
import jakarta.mail.MessagingException;

/**
 * @Author ZhaoMuse
 * @date 2022/11/18
 * @Since 1.0
 */
public interface InformService {
    void send(DataError dataError) throws Exception;
}
