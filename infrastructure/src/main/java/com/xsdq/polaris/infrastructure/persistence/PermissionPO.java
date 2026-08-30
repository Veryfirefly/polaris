package com.xsdq.polaris.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("permissions")
public class PermissionPO {

    @TableId
    private Long id;
    private String name;
    private String code;
    private Short status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
