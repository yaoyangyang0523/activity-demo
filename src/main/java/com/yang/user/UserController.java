package com.yang.user;

import com.yang.user.entity.User;
import com.yang.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * explain：用户控制类
 *
 * @author yang
 * @date 2021/1/1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @PostMapping("/queryList")
    public List<User> queryList() {
        return userService.queryList();
    }

}
