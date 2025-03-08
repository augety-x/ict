package com.ftt.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO  implements Serializable {

    String username;
    String password;

}
