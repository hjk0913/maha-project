package com.maha.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SchedulerJob2 extends QuartzJobBean
{
    private SchedulerTask2 schedulerTask2;

    public void setSchedulerTask2(SchedulerTask2 schedulerTask2) {
        this.schedulerTask2 = schedulerTask2;
    }

    protected void executeInternal(JobExecutionContext context)
    throws JobExecutionException {

        schedulerTask2.printSchedulerMessage();

    }
}