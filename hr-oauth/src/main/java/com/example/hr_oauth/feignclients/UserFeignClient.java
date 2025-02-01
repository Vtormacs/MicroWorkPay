package com.example.hr_oauth.feignclients;

import com.example.hr_oauth.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "hr-user", path = "/api/users")
public interface UserFeignClient {

    @GetMapping("/search")
    ResponseEntity<User> getUserForEmail(@RequestParam String email);

}
