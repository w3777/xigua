package com.xigua.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/9 15:11
 */
@TableName("user")
@Data
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String name;
    private int age;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private Integer pageStart;
    private Integer pageSize;
}
