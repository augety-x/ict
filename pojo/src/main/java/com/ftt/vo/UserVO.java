package com.ftt.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Integer userId;
    private String username;
    private int status;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
