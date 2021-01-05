package com.yang.workOrder;

import com.alibaba.fastjson.JSONObject;
import com.yang.utils.Constants;
import com.yang.utils.Dto;
import com.yang.utils.Page;
import com.yang.workOrder.entity.WorkOrder;
import com.yang.workOrder.service.IWorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * explain：工单控制类
 *
 * @author yang
 * @date 2021/1/1
 */
@RestController
@RequestMapping("/workOrder")
public class WorkOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderController.class);

    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * 创建工单
     * @param WorkOrder
     * @return
     */
    @PostMapping("/insertData")
    public Dto insertData(@RequestBody WorkOrder WorkOrder) {
        boolean b = workOrderService.insertData(WorkOrder);
        if (b) {
            return new Dto(Constants.SUCCESS, "新增成功");
        }
        return new Dto(Constants.ERROR, "新增失败");
    }

    /**
     * 删除数据
     * @param object
     * @return
     */
    @PostMapping("/deleteData")
    public Dto deleteData(@RequestBody JSONObject object) {
        boolean b = workOrderService.deleteData(object.getIntValue("id"));
        if (b) {
            return new Dto(Constants.SUCCESS, "删除成功");
        }
        return new Dto(Constants.ERROR, "删除失败");
    }

    /**
     * 获取工单集合
     * @param object
     * @return
     */
    @PostMapping("/queryList")
    public Page<WorkOrder> queryList(@RequestBody JSONObject object) {
        return workOrderService.queryList(object);
    }

    /**
     * 待审批工单集合
     * @param object
     * @return
     */
    @PostMapping("/querySpList")
    public Page<JSONObject> querySpList(@RequestBody JSONObject object) {
        return workOrderService.querySpList(object);
    }



    /**
     * 开始流程
     * @param object
     * @return
     */
    @PostMapping("/startProcess")
    public Dto startProcess(@RequestBody JSONObject object) {
        boolean b = workOrderService.startProcess(object.getIntValue("id"));
        if (b) {
            return new Dto(Constants.SUCCESS, "提交成功,开始流程");
        }
        return new Dto(Constants.ERROR, "提交失败");
    }

    /**
     * 工单撤回
     * @param object
     * @return
     */
    @PostMapping("/backProcess")
    public Dto backProcess(@RequestBody JSONObject object) {
        boolean b = workOrderService.backProcess(object.getIntValue("id"));
        if (b) {
            return new Dto(Constants.SUCCESS, "撤回成功");
        }
        return new Dto(Constants.ERROR, "撤回失败");
    }

    /**
     * 审批工单
     * @param object
     * @return
     */
    @PostMapping("/approvalProcess")
    public Dto approvalProcess(@RequestBody JSONObject object) {
        boolean b = workOrderService.approvalProcess(object);
        if (b) {
            return new Dto(Constants.SUCCESS, "审批成功");
        }
        return new Dto(Constants.ERROR, "审批失败");
    }

    /**
     * 工单流转
     * @param object
     * @return
     */
    @PostMapping("/moveProcess")
    public List<JSONObject> moveProcess(@RequestBody JSONObject object) {
        return workOrderService.moveProcess(object.getIntValue("id"));
    }
}
