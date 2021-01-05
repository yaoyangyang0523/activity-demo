package com.yang.workOrder.service;

import com.alibaba.fastjson.JSONObject;
import com.yang.utils.Page;
import com.yang.workOrder.entity.WorkOrder;

import java.util.List;

/**
 * explain：工单服务层接口
 *
 * @author yang
 * @date 2021/1/3
 */
public interface IWorkOrderService {

    /**
     * 新增工单
     * @param WorkOrder
     * @return
     */
    boolean insertData(WorkOrder WorkOrder);

    /**
     * 删除工单
     * @param id
     * @return
     */
    boolean deleteData(Integer id);

    /**
     * 获取工单集合
     * @param object
     * @return
     */
    Page<WorkOrder> queryList(JSONObject object);

    /**
     * 待审批工单集合
     * @param object
     * @return
     */
    Page<JSONObject> querySpList(JSONObject object);

    /**
     * 开始流程
     * @param id
     * @return
     */
    boolean startProcess(Integer id);

    /**
     * 撤回
     * @param id
     * @return
     */
    boolean backProcess(Integer id);

    /**
     * 工单流转
     * @param id
     * @return
     */
    List<JSONObject> moveProcess(Integer id);

    /**
     * 工单审批
     * @param object
     * @return
     */
    boolean approvalProcess(JSONObject object);
}
