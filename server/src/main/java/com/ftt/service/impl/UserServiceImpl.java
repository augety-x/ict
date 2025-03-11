package com.ftt.service.impl;


import com.ftt.constant.MessageConstant;
import com.ftt.constant.StatusConstant;
import com.ftt.dto.LoginDTO;
import com.ftt.dto.RegisterDTO;
import com.ftt.entity.User;
import com.ftt.exception.AccountLockedException;
import com.ftt.exception.AccountNotFoundException;
import com.ftt.exception.PasswordErrorException;
import com.ftt.exception.UsernameAlreadyExistsException;
import com.ftt.mapper.UserMapper;
import com.ftt.service.UserService;
import com.ftt.vo.RegisterVO;
import com.ftt.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    UserMapper userMapper;
    public User login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);

        // 2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        // 对密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            // 账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        // 3、更新上次登录时间
        user.setLastLogin(LocalDateTime.now());
        userMapper.updateLastLoginTime(Math.toIntExact(user.getUserId()), user.getLastLogin());
        // 3、返回实体对象
        return user;
    }
    public RegisterVO register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();

        // 检查用户名是否已存在
        User existingUser = userMapper.getByUsername(username);
        if (existingUser != null) {
            throw new UsernameAlreadyExistsException("用户名已存在");
        }

        // 对密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        // 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        user.setStatus(StatusConstant.ENABLE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        // 保存用户
//        userMapper.myinsert(user);
        userMapper.insert(user);
        // 返回注册结果
        RegisterVO registerVO = new RegisterVO();
        registerVO.setUserId(Math.toIntExact(user.getUserId()));
        registerVO.setUsername(user.getUsername());
        // 完成角色初始化

        return registerVO;
    }
    public UserVO getUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        UserVO userVO =new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
