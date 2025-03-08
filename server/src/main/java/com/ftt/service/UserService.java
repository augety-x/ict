package com.ftt.service;

import com.ftt.dto.LoginDTO;
import com.ftt.entity.User;


public interface UserService {
    public User login(LoginDTO loginDTO);


}
