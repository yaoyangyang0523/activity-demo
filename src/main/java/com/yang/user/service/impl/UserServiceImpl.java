package com.yang.user.service.impl;

import com.yang.user.dao.IUserDao;
import com.yang.user.entity.User;
import com.yang.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    @Override
    public String randomSpUser(int num) {
        List<User> userList = userDao.queryList();
        List<String> stringList = userList.stream().map(User::getUserName).collect(Collectors.toList());
        int size = stringList.size();
        String spUser;
        // 数据库没有那么多的人
        int length = num > size ? size : num;
        if (length == size) {
            spUser = stringList.stream().map(String::valueOf).collect(Collectors.joining(","));
        } else {
            List<String> strings = new ArrayList<>();
            // 随机抽取幸运用户
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(size);
                strings.add(stringList.get(number));
                stringList.remove(number);
                size --;
            }
            spUser = strings.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
        LOGGER.info("随机审批人：" + spUser);
        return spUser;
    }
}
