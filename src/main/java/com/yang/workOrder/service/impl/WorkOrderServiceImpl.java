package com.yang.workOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yang.config.ServiceLocator;
import com.yang.user.entity.User;
import com.yang.user.service.IUserService;
import com.yang.utils.Page;
import com.yang.utils.StrUtils;
import com.yang.workOrder.dao.IWorkOrderDao;
import com.yang.workOrder.entity.WorkOrder;
import com.yang.workOrder.service.IWorkOrderService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * explain：工单服务层接口实现类
 *
 * @author yang
 * @date 2021/1/3
 */
@Service
public class WorkOrderServiceImpl implements IWorkOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private IWorkOrderDao workOrderDao;
    @Autowired
    private IUserService userService;
    @Autowired
    private ServiceLocator serviceLocator;

    @Override
    public boolean insertData(WorkOrder WorkOrder) {
        WorkOrder.setSqNum(StrUtils.randomSqNo(7));
        WorkOrder.setStartTime(System.currentTimeMillis());
        return workOrderDao.insertData(WorkOrder) > 0;
    }

    @Override
    public boolean deleteData(Integer id) {
        return workOrderDao.delData(id) > 0;
    }

    @Override
    public Page<WorkOrder> queryList(JSONObject object) {
        Page.handlePage(object);
        int num = object.getIntValue("pageNumber");
        int size = object.getIntValue("pageSize");

        int total = workOrderDao.queryCount(object);
        List<WorkOrder> list = workOrderDao.queryList(object);

        return new Page(list, num, size, total);
    }

    @Override
    public Page<JSONObject> querySpList(JSONObject object) {
        Page.handlePage(object);
        int num = object.getIntValue("pageNumber");
        int size = object.getIntValue("pageSize");

        int total = workOrderDao.querySpCount(object);
        List<JSONObject> list = workOrderDao.querySpList(object);

        return new Page(list, num, size, total);
    }

    /*********************************************************流程相关**************************************************/

    @Override
    public boolean startProcess(Integer id) {
        WorkOrder workOrder = workOrderDao.queryById(id);
        return serviceLocator.getService(workOrder.getProcessName()).startProcess(workOrder);
    }

    @Override
    public boolean backProcess(Integer id) {
        WorkOrder workOrder = workOrderDao.queryById(id);
        return serviceLocator.getService(workOrder.getProcessName()).backProcess(workOrder);
    }

    @Override
    public boolean approvalProcess(JSONObject object) {
        int id = object.getIntValue("id");
        WorkOrder workOrder = workOrderDao.queryById(id);
        return serviceLocator.getService(workOrder.getProcessName()).approvalProcess(workOrder, object);
    }

    @Override
    public List<JSONObject> moveProcess(Integer id) {
        WorkOrder workOrder = workOrderDao.queryById(id);

        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(workOrder.getProcessId()).list();
        // 转为map
        Map<String, String> map = list.stream().collect(Collectors.toMap(HistoricTaskInstance::getTaskDefinitionKey, HistoricTaskInstance::getId));


        //根据流程id获得流程模式id
        String processDefinitionId  = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(workOrder.getProcessId()).singleResult().getProcessDefinitionId();
        //获得流程模型
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        // 审批环节
        List<JSONObject> spLink = new ArrayList<>();
        JSONObject obj;
        //获得流程模型的所有节点
        Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
        for(FlowElement e : flowElements) {
            if (e instanceof UserTask) {
                obj = new JSONObject();
                obj.put("taskName", e.getName());
                // 审批人
                String sp1 = ((UserTask) e).getAssignee();
                if (StringUtils.isNotBlank(sp1)) {
                    if (sp1.contains("#")) {
                        obj.put("spUser", "动态审批人");
                    } else {
                        User u = userService.queryByUserName(sp1);
                        obj.put("spUser", u.getName());
                    }
                }
                List<String> sp2 = ((UserTask) e).getCandidateUsers();
                if (sp2 != null && sp2.size() > 0) {
                    String spUser = "";
                    for (String s : sp2) {
                        if (StringUtils.isNotBlank(s)) {
                            if (s.contains("#")) {
                                spUser = "动态审批人";
                                break;
                            } else {
                                User u = userService.queryByUserName(s);
                                spUser += "".equals(spUser) ? u.getName() : "、" + u.getName();
                            }
                        }
                    }
                    obj.put("spUser", spUser);
                }

                // 查询历史任务，获取批注信息
                if (map.containsKey(e.getId())) {
                    List<Comment> comments = taskService.getTaskComments(map.get(e.getId()));
                    if (comments != null && comments.size() > 0) {
                        obj.put("spResult", comments.get(0).getFullMessage());
                    } else {
                        obj.put("spResult", "");
                    }
                } else {
                    obj.put("spResult", "");
                }

                spLink.add(obj);
            }
        }

        return spLink;
    }
}
