package com.ftt.controller;


import com.ftt.constant.JwtClaimsConstant;
import com.ftt.constant.MessageConstant;
import com.ftt.dto.LoginDTO;
import com.ftt.dto.RegisterDTO;
import com.ftt.entity.JwtProperties;
import com.ftt.entity.User;
import com.ftt.exception.AccountLockedException;
import com.ftt.exception.AccountNotFoundException;
import com.ftt.exception.PasswordErrorException;
import com.ftt.exception.UsernameAlreadyExistsException;
import com.ftt.result.Result;
import com.ftt.service.UserService;
import com.ftt.utils.JwtUtil;
import com.ftt.vo.LoginVO;
import com.ftt.vo.RegisterVO;
import com.ftt.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "员工管理")
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("员工登录：{}", loginDTO);

        try {
            User user = userService.login(loginDTO);

            //登录成功后，生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);

            LoginVO loginVO = LoginVO.builder()
                    .id(user.getUserId())
                    .userName(user.getUsername())
                    .token(token)
                    .build();

            return Result.success(loginVO);
        } catch (AccountNotFoundException e) {
            // 账号不存在
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        } catch (PasswordErrorException e) {
            // 密码错误
            return Result.error(MessageConstant.PASSWORD_ERROR);
        } catch (AccountLockedException e) {
            // 账号被锁定
            return Result.error(MessageConstant.ACCOUNT_LOCKED);
        }
    }
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        log.info("用户注册：{}", registerDTO);

        try {
            RegisterVO registerVO = userService.register(registerDTO);
            return Result.success(registerVO);
        } catch (UsernameAlreadyExistsException e) {
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public Result<UserVO> getUserInfo(@PathVariable("user_id") Integer userId) {
        // 模拟从数据库或其他数据源获取用户信息
        UserVO userVO = userService.getUserInfo(userId);


        return Result.success(userVO);
    }
}