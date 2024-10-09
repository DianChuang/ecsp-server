package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ManageService {
    private static GlobalConfiguration staticglobalConfiguration;
    @Resource
    private GlobalConfiguration globalConfiguration;

    @PostConstruct
    public void init() {
        staticglobalConfiguration = globalConfiguration;
    }

}
