package com.yang.user.service.impl;

import com.yang.user.dao.IUserDao;
import com.yang.user.entity.User;
import com.yang.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * explain：用户服务层接口实现类
 *
 * @author yang
 * @date 2021/1/1
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> queryList() {
        return userDao.queryList();
    }

    @Override
    public User queryByUserName(String userName) {
        return userDao.queryByUserName(userName);
    }
}
