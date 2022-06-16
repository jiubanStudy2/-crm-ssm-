package com.bjpowernode.settings.web.service;

import com.bjpowernode.settings.web.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


public interface UserService {

    User selectUserByActAndPwd(Map<String,Object> map);
}
