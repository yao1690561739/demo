package com.yao.system.dao;

import com.yao.system.dto.ActionLogDto;
import com.yao.system.entity.ActionLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YL
 * @date 2020/11/23 9:54
 * @description 操作日志接口
 */
@Mapper
public interface ActionLogDao {
    /**
     * 根据ID获取操作日志
     * @param id 日志ID
     * @return 日志信息
     */
    ActionLog getActionLogById(Integer id);
    /**
     * 按条件获取日志信息列表
     * @param actionLogDto 实体对象
     * @return 日志信息
     */
    List<ActionLog> getActionLogs(ActionLogDto actionLogDto);
    /**
     * 添加日志信息
     * @param actionLog 实体对象
     * @return 影响行数
     */
    int addActionLog(ActionLog actionLog);
    /**
     * 批量删除日志信息
     * @param ids 日志ID
     * @return 影响行数
     */
    int removeActionLogs(String[] ids);
    /**
     * 清空日志信息
     * @return 影响行数
     */
    int removeAllActionLogs();
}
