package com.yang.workOrder.dao;

import com.alibaba.fastjson.JSONObject;
import com.yang.workOrder.entity.WorkOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * explain：工单持久层
 *
 * @author yang
 * @date 2021/1/3
 */
@Mapper
public interface IWorkOrderDao {

    /**
     * 新增
     * @param workOrder
     * @return
     */
    int insertData(WorkOrder workOrder);

    /**
     * 更新
     * @param workOrder
     * @return
     */
    int updateData(WorkOrder workOrder);

    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    WorkOrder queryById(Integer id);

    /**
     * 获取工单集合
     * @param object
     * @return
     */
    List<WorkOrder> queryList(JSONObject object);

    /**
     * 获取数量
     * @param object
     * @return
     */
    int queryCount(JSONObject object);

    /**
     * 获取工单集合
     * @param object
     * @return
     */
    List<JSONObject> querySpList(JSONObject object);

    /**
     * 获取数量
     * @param object
     * @return
     */
    int querySpCount(JSONObject object);

    /**
     * 删除工单
     * @param id
     * @return
     */
    int delData(Integer id);
}
