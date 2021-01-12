package com.yang.user.service;

import com.yang.user.entity.User;

import java.util.List;

/**
 * explain：用户服务层接口
 *
 * @author yang
 * @date 2021/1/1
 */
public interface IUserService {

    /**
     * 获取所有用户
     * @return
     */
    List<User> queryList();

    /**
     * 根据账号获取用户
     * @param userName
     * @return
     */
    User queryByUserName(String userName);

    /**
     * 随机生成审批人员
     * @param num
     * @return
     */
    String randomSpUser(int num);
}
