package com.dcstd.web.ecspserver.common;

import com.dcstd.web.ecspserver.mapper.CronMapper;
import com.dcstd.web.ecspserver.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @FileName MyTask
 * @Description 定时器，暂时搁置
 * @Author fazhu
 * @date 2024-08-14
 **/

@Component
public class MyTask implements SchedulingConfigurer{
    @Autowired
    private CronMapper cronMapper;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(()-> use(),
                triggerContext -> {
                    String cron = time();
                    if (cron.isEmpty()) {
                        System.out.println("cron is null");
                    }
                    return new CronTrigger(cron).nextExecutionTime(triggerContext).toInstant();
                }
                );
    }
    public void use(){

    }
    public String time(){
        return DateUtil.getCron(new Date());
    }
}
