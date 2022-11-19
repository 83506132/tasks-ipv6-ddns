package com.musebk.resolution.service.impl;

import com.musebk.resolution.domain.DataError;
import com.musebk.resolution.service.InformService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * @Author ZhaoMuse
 * @date 2022/11/19
 * @Since
 */
@Service
@ConditionalOnMissingBean({InformService.class})
public class DefaultInformService implements InformService {

    @Override
    public void send(DataError dataError) throws Exception {}
}
