package com.yang.workOrder.entity;

/**
 * explain：工单表实体
 *
 * @author yang
 * @date 2021/1/3
 */
public class WorkOrder {

    private Integer id;

    private String sqNum;

    private Long startTime;

    private String userName;

    private String status;

    private String processId;

    private String processName;

    private String taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSqNum() {
        return sqNum;
    }

    public void setSqNum(String sqNum) {
        this.sqNum = sqNum;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
