package com.yun.demo.vo;

import com.yun.demo.valid.Age;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 10000000000000000L, groups = Update.class)
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = {Save.class, Update.class})
    @Length(min=3,max = 20, message = "用户名不能超过20个字符")
    @Pattern(regexp = "^[\\\u4E00-\\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String username;

    // 自定义校验
    @Age(message="年龄不在合法范围内", minAge=0, maxAge=120)
    private Integer age;

    @NotNull(groups = {Save.class, Update.class})
    // 嵌套校验，必须使用 @Valid
    @Valid
    private List<Job> jobs;

    private String desc;

    private List<String> tags;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    @NotBlank(message = "联系邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    /**
     * 密码
     */
    private String password;

    /*** 创建时间 */
    @Future(message = "时间必须是将来时间")
    private Date createTime;

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }

    @Data
    public static class Job {

        @Min(value = 1, groups = Update.class)
        private Long jobId;

        @NotNull(groups = {Save.class, Update.class})
        @Length(min = 2, max = 10, groups = {Save.class, Update.class})
        private String jobName;

        @NotNull(groups = {Save.class, Update.class})
        @Length(min = 2, max = 10, groups = {Save.class, Update.class})
        private String position;
    }
}