package com.yang.workOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yang.workOrder.entity.WorkOrder;
import com.yang.workOrder.service.IRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * explain：A_002流程审批实现类
 *
 * @author yang
 * @date 2021/1/5
 */
@Service("A_002")
public class RootA002ServiceImpl implements IRootService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootA002ServiceImpl.class);

    @Override
    public boolean startProcess(WorkOrder workOrder) {
        return false;
    }

    @Override
    public boolean backProcess(WorkOrder workOrder) {
        return false;
    }

    @Override
    public boolean approvalProcess(WorkOrder workOrder, JSONObject object) {
        return false;
    }
}
