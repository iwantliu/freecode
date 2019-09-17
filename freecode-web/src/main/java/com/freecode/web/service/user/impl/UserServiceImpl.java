package com.freecode.web.service.user.impl;

import com.freecode.web.service.user.UserService;
import com.freecode.web.service.user.remote.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-22 15:49
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public Map<String, String> userList() {
        return remoteUserService.userList();
    }
}
