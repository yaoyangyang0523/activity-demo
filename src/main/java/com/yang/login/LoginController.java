package com.yang.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * explain：登录控制类
 *
 * @author yang
 * @date 2021/1/1
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "login";
    }

}
