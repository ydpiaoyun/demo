package com.yun.demo.vo;

import lombok.Data;

/**
 * @author zxy
 */
@Data
public class UserTokenDTO {

    private String userId;

    private String username;

    private String password;

    private Integer sex;

    private String age;
}
