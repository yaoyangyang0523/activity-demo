package com.yang.workOrder.service;

import com.alibaba.fastjson.JSONObject;
import com.yang.workOrder.entity.WorkOrder;

/**
 * explain：基础流程操作服务接口
 *
 * @author yang
 * @date 2021/1/5
 */
public interface IRootService {

    /**
     * 开始流程
     * @param workOrder
     * @return
     */
    boolean startProcess(WorkOrder workOrder);

    /**
     * 流程撤回
     * @param workOrder
     * @return
     */
    boolean backProcess(WorkOrder workOrder);

    /**
     * 流程审批
     * @param workOrder
     * @param object
     * @return
     */
    boolean approvalProcess(WorkOrder workOrder, JSONObject object);
}
