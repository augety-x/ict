package com.ftt.service.impl;


import com.ftt.constant.MessageConstant;
import com.ftt.constant.StatusConstant;
import com.ftt.dto.LoginDTO;
import com.ftt.entity.User;
import com.ftt.exception.AccountLockedException;
import com.ftt.exception.AccountNotFoundException;
import com.ftt.exception.PasswordErrorException;
import com.ftt.mapper.UserMapper;
import com.ftt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

        // 3、返回实体对象
        return user;
    }

}
