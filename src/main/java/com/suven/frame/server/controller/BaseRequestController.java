package com.suven.frame.server.controller;

import com.suven.frame.server.data.vo.ResponseResultVO;

import com.suven.frame.server.exception.SysMsgEnumType;
import com.suven.frame.server.handler.OutputMessage;
import com.suven.frame.server.handler.ResponseJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.suven.frame.server.data.model.*;

/**
 * Created by lipingfa on 2017/6/16.
 */
@Controller
public class BaseRequestController {
    /**
     * get请求
     *
     * @return
     */
    @GetMapping("/user/info")
    public ResponseResultVO getUser() {
        ResponseResultVO responseResultVO = new ResponseResultVO();
        responseResultVO.setData(true);
        User user = new User("jady", 12);
        responseResultVO.setResponse(user);
        return responseResultVO;
    }

    @RequestMapping("/user/login")
//    @ResponseJson
    public void login(OutputMessage output, @RequestParam String name, @RequestParam String password) {
        ResponseResultVO responseResultVO = new ResponseResultVO();

        if ("jady".equals(name) && "1234".equals(password)) {
            responseResultVO.setData(true);
            responseResultVO.setResponse("addafeas_cdedhyuj_用户名信息——daledage_leiaefss");
        } else {
            responseResultVO.setData(false);
            responseResultVO.setCode(1000);
            responseResultVO.setMsg("用户名或密码错误");
        }
        output.write(false);
        return ;
    }

    @RequestMapping(path = "/user/login/json")
    @ResponseJson
    public Object login( UserForLogin userForLogin) {
        ResponseResultVO responseResultVO = new ResponseResultVO();
//        if(true)
//          throw new SystemRuntimeException(SysMsgEnumType.SYS_INVALID_REQUEST.setErrorMsg("APPID 为0"));
        if ("jady".equals(userForLogin.getName()) && "1234".equals(userForLogin.getPassword())) {
            responseResultVO.setData(true);
            responseResultVO.setResponse("addafeas_cdedhyuj_用户名信息——daledage_leiaefss");
        } else {
            responseResultVO.setData(false);
            responseResultVO.setCode(1000);
            responseResultVO.setResponse("用户名或密码错误");

        }
//        output.write(responseResultVO);
//        return responseResultVO;
        return SysMsgEnumType.SYS_INVALID_REQUEST.setErrorMsg("测试架构异常");
    }

    @PutMapping(path = "/user/update")
    public ResponseResultVO updateUserInfo(@RequestHeader("access_token") String accessToken, @RequestParam(required = false) String name, @RequestParam(required = false) String age) {
        ResponseResultVO responseResultVO = new ResponseResultVO();
        if ("1234".equals(accessToken)) {
            //更新用户信息
            responseResultVO.setData(true);
        } else {
            responseResultVO.setData(false);
            responseResultVO.setCode(10001);
            responseResultVO.setMsg("更新接口token错误");
        }
        return responseResultVO;
    }

    @DeleteMapping(path = "/feed/delete")
    public ResponseResultVO deleteFeed(@RequestHeader("access_token") String accessToken, @RequestBody DeleteBody body) {
        ResponseResultVO responseResultVO = new ResponseResultVO();
        if ("1234".equals(accessToken)) {
            //删除feed
            responseResultVO.setData(true);
        } else {
            responseResultVO.setData(false);
            responseResultVO.setCode(10001);
            responseResultVO.setResponse("删除接口token错误");
        }
        return responseResultVO;
    }
}
