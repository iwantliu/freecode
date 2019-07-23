package com.freecode.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description:用户服务中心</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-18 16:18
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, String> userList() {
        Map<String, String> map = new HashMap<>();
        map.put("1001", "user-01");
        map.put("1002", "user-02");
        map.put("1003", "user-03");
        return map;
    }
}
