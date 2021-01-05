package com.yang.user.entity;

/**
 * explain：用户实体类
 *
 * @author yang
 * @date 2021/1/1
 */
public class User {

    private Integer id;

    private String userName;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
