package com.yang.workOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yang.user.entity.User;
import com.yang.user.service.IUserService;
import com.yang.utils.Constants;
import com.yang.workOrder.dao.IWorkOrderDao;
import com.yang.workOrder.entity.WorkOrder;
import com.yang.workOrder.service.IRootService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * explain：A_002流程审批实现类
 *
 * @author yang
 * @date 2021/1/5
 */
@Service("A_002")
public class RootA002ServiceImpl implements IRootService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootA002ServiceImpl.class);

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IWorkOrderDao workOrderDao;
    @Autowired
    private IUserService userService;

    @Override
    public boolean startProcess(WorkOrder workOrder) {
        Map<String, Object> var = new HashMap<>(16);
        // 放入三个随机审批人
        var.put("spLeader1", userService.randomSpUser(3));
        // 流程ID
        String processId = runtimeService.startProcessInstanceByKey(workOrder.getProcessName(), var).getId();
        workOrder.setProcessId(processId);
        workOrder.setStatus(Constants.STATUS_1);
        workOrderDao.updateData(workOrder);
        return true;
    }

    @Override
    public boolean backProcess(WorkOrder workOrder) {
        // 删除流程实例与历史记录
        runtimeService.deleteProcessInstance(workOrder.getProcessId(), "撤回删除流程实例");
        historyService.deleteHistoricProcessInstance(workOrder.getProcessId());
        workOrder.setStatus(Constants.STATUS_0);
        workOrderDao.updateData(workOrder);
        return true;
    }

    @Override
    public boolean approvalProcess(WorkOrder workOrder, JSONObject object) {
        String taskId = object.getString("taskId");
        String pass = object.getString("pass");
        String spUser = object.getString("spUser");
        Task task;
        // 不验证管理员，他就是任性
        if (!Constants.ADMIN.equals(spUser)) {
            // 根据流程ID和审批人
            task = taskService.createTaskQuery().processInstanceId(workOrder.getProcessId()).taskCandidateOrAssigned(spUser).singleResult();
        } else {
            task = taskService.createTaskQuery().processInstanceId(workOrder.getProcessId()).singleResult();
        }

        if (task == null || !taskId.equals(task.getId())) {
            LOGGER.info("当前节点任务已被审批或审批结束");
            return false;
        }

        if (Constants.PASS.equals(pass)) {
            return taskPass(workOrder, object, task);
        }
        return taskNoPass(workOrder, object, task);
    }

    /**
     * 通过审批
     * @return
     */
    private boolean taskPass(WorkOrder workOrder, JSONObject object, Task task) {
        String result = object.getString("result");
        String spUser = object.getString("spUser");
        User user = userService.queryByUserName(spUser);

        Map<String, Object> var = new HashMap<>(16);
        var.put("status", Constants.PASS);

        boolean ifSpOver = false;
        switch (task.getTaskDefinitionKey()) {
            case "userTask1" :
                var.put("spLeader2", userService.randomSpUser(1));
                break;
            case "userTask2" :
                ifSpOver = true;
                break;
            default : return false;
        }

        // 添加批注，当前节点信息，方便查看，也可以不添加
        String comment = MessageFormat.format("{0} - {1} - 意见：{2}", user.getName(), "通过", result);
        taskService.addComment(task.getId(), workOrder.getProcessId(), comment);
        // 提交任务，开始下一个环节
        taskService.complete(task.getId(), var);
        // 处理工单
        if (ifSpOver) {
            workOrder.setStatus(Constants.STATUS_3);
            workOrderDao.updateData(workOrder);
        }
        return true;
    }

    /**
     * 不通过审批
     * @return
     */
    private boolean taskNoPass(WorkOrder workOrder, JSONObject object, Task task) {
        String result = object.getString("result");
        String spUser = object.getString("spUser");
        User user = userService.queryByUserName(spUser);

        Map<String, Object> var = new HashMap<>(16);
        var.put("status", Constants.NO_PASS);
        // 添加批注，当前节点信息，方便查看，也可以不添加
        String comment = MessageFormat.format("{0} - {1} - 意见：{2}", user.getName(), "不通过", result);
        taskService.addComment(task.getId(), workOrder.getProcessId(), comment);
        // 提交任务，开始下一个环节
        taskService.complete(task.getId(), var);
        // 处理工单
        workOrder.setStatus(Constants.STATUS_2);
        workOrderDao.updateData(workOrder);
        return true;
    }
}
