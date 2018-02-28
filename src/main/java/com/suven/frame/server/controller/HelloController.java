package com.suven.frame.server.controller;

import com.suven.frame.server.data.model.HelloWorld;
import com.suven.frame.server.data.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lipingfa on 2017/6/15.
 */
@RestController
public class HelloController {

    private HelloWorld helloWorld;

    public HelloController(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return helloWorld.hello();
    }

    @PostMapping(value = "login")
    public void login(@RequestHeader("access_token") String accessToken,@RequestParam String name, @RequestParam String password) {
        System.out.println("accessToken:" + accessToken);
    }

    @RequestMapping(path = "/{account}", method = RequestMethod.GET)
    public String getUser(@PathVariable("account") String phoneNumber) {
        return phoneNumber;
    }

    @PostMapping(path = "register")
    public String registerUser(@RequestBody User json) {
        return json.toString();
    }


}
