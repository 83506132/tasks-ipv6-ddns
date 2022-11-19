package com.musebk.resolution.domain;

import lombok.Data;

/**
 * @Author ZhaoMuse
 * @date 2022/11/18
 * @Since 1.0
 */
@Data
public class DataError {
    public static final String TEMPLATE_NAME = "ErrorTemplate.mail";

    private String title;

    private String context;

    public DataError(String title, String context) {
        this.title = title;
        this.context = context;
    }
}
