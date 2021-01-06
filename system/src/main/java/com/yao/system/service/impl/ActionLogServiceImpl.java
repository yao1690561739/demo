package com.yao.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.yao.system.dao.ActionLogDao;
import com.yao.system.dto.ActionLogDto;
import com.yao.system.entity.ActionLog;
import com.yao.system.service.ActionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YL
 * @date 2020/11/23 14:44
 * @description 操作记录实现类
 */
@Service
@Slf4j
public class ActionLogServiceImpl implements ActionLogService {

    @Resource
    private ActionLogDao actionLogDao;

    /**
     * 根据ID获取操作日志
     *
     * @param id 日志ID
     * @return 日志信息
     */
    @Override
    public ActionLog getActionLogById(Integer id) {
        log.info("查询操作日志信息！");
        return actionLogDao.getActionLogById(id);
    }

    /**
     * 按条件获取日志信息列表
     *
     * @param actionLogDto 实体对象
     * @return 日志信息
     */
    @Override
    public List<ActionLog> getActionLogs(ActionLogDto actionLogDto) {
        log.info("查询操作日志列表！");
        return actionLogDao.getActionLogs(actionLogDto);
    }

    /**
     * 添加日志信息
     *
     * @param actionLog 实体对象
     * @return 影响行数
     */
    @Override
    public int addActionLog(ActionLog actionLog) {
        log.info("添加操作日志信息！");
        return actionLogDao.addActionLog(actionLog);
    }

    /**
     * 批量删除日志信息
     *
     * @param ids 日志ID
     * @return 影响行数
     */
    @Override
    public int removeActionLogs(String ids) {
        log.info("批量删除日志信息！");
        return actionLogDao.removeActionLogs(Convert.toStrArray(ids));
    }

    /**
     * 清空日志信息
     *
     * @return 影响行数
     */
    @Override
    public int removeAllActionLogs() {
        log.info("清空操作日志信息！");
        return actionLogDao.removeAllActionLogs();
    }
}
