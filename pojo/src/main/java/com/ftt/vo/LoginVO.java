package com.ftt.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private Integer id;
    private String userName;
    private String token;

}
