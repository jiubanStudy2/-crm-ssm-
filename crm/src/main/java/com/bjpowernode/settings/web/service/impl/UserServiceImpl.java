package com.bjpowernode.settings.web.service.impl;

import com.bjpowernode.settings.web.domain.User;
import com.bjpowernode.settings.web.mapper.UserMapper;
import com.bjpowernode.settings.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }
}
