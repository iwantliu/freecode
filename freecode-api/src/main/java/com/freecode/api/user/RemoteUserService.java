package com.freecode.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * <p>Description:用户远程服务</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-18 16:56
 */
@FeignClient(value = "consul-provider-user")
public interface RemoteUserService {

    @GetMapping("/user/list")
    Map<String, String> userList();
}
