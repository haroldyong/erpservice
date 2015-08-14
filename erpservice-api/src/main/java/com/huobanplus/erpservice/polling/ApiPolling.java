package com.huobanplus.erpservice.polling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * API端轮询组件
 */
@Component
public class ApiPolling {

    private static Logger logger = LoggerFactory.getLogger(ApiPolling.class.getName());
    /**
     * Spring Task 轮询测试
     * 每天每隔2小时轮询一次 cron 可配置
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    private void polling()
    {
        //
        logger.info("这是个轮询");
    }
}
