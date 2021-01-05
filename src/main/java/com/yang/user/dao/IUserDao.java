package com.yang.user.dao;

import com.yang.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * explain：用户持久层
 *
 * @author yang
 * @date 2021/1/1
 */
@Mapper
public interface IUserDao {

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

}
