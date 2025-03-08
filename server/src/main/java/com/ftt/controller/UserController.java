package com.ftt.controller;


import com.ftt.constant.JwtClaimsConstant;
import com.ftt.dto.LoginDTO;
import com.ftt.entity.JwtProperties;
import com.ftt.entity.User;
import com.ftt.result.Result;
import com.ftt.service.UserService;
import com.ftt.utils.JwtUtil;
import com.ftt.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserService userService;
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<LoginVO> login(@RequestBody LoginDTO LoginDTO) {
        log.info("员工登录：{}", LoginDTO);

        User user = userService.login(LoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        LoginVO loginVO = LoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .token(token)
                .build();

        return Result.success(loginVO);
    }

}
