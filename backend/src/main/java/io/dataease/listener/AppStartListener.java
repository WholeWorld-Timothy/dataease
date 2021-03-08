package io.dataease.listener;

import io.dataease.base.domain.DatasetTableTask;
import io.dataease.job.sechedule.ScheduleManager;
import io.dataease.service.ScheduleService;
import io.dataease.service.dataset.DataSetTableTaskService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AppStartListener implements ApplicationListener<ApplicationReadyEvent> {
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private DataSetTableTaskService dataSetTableTaskService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("================= 应用启动 =================");
        /* cron schedule */
//        scheduleManager.addCronJob(new JobKey("abc", "def"), new TriggerKey("abc", "def"), TestJob.class, "*/10 * * * * ?");
        /* single schedule*/
//        long timestamp = System.currentTimeMillis() + 90 * 1000;
//        Date date = new Date(timestamp);
//        scheduleManager.addSingleJob(new JobKey("abc", "def"), new TriggerKey("abc", "def"), TestJob.class, date);
        // 项目启动，从数据库读取任务加入到Quartz
        List<DatasetTableTask> list = dataSetTableTaskService.list(new DatasetTableTask());
        for (DatasetTableTask task : list) {
            try {
                scheduleService.addSchedule(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}