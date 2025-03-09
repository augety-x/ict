package com.ftt.service;

import com.ftt.dto.LoginDTO;
import com.ftt.dto.RegisterDTO;
import com.ftt.entity.User;
import com.ftt.vo.RegisterVO;
import com.ftt.vo.UserVO;


public interface UserService {
    public User login(LoginDTO loginDTO);
    public RegisterVO register(RegisterDTO registerDTO);
    public UserVO getUserInfo(Integer id);
}
