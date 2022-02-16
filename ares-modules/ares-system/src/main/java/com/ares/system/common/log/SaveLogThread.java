package com.ares.system.common.log;


import com.ares.core.model.system.SysLog;
import com.ares.system.service.SysLogService;

/**
 * @description:
 * @author: Young 2020/05/08
 **/
public class SaveLogThread implements Runnable {
    private SysLog sysLog;
    private SysLogService sysLogService;

    public SaveLogThread(SysLog sysLog, SysLogService sysLogService) {
        this.sysLog = sysLog;
        this.sysLogService = sysLogService;
    }

    @Override
    public void run() {
        sysLogService.insert(sysLog);
    }
}
