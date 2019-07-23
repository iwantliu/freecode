package com.freecode.service.user.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(value = "consul-provider-user")
public interface RemoteUserService {

    @GetMapping("/user/list")
    Map<String, String> userList();
}