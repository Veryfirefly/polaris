package com.xsdq.polaris.repository.po;

import java.time.LocalDateTime;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.repository.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("users")
public class UserPO {

    @TableId
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private Status status;
    private String email;
    private String phone;
    private String address;
    private String avatarPath;
    private Long tenantId;
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;
}
