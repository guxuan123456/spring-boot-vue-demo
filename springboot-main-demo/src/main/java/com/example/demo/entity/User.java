package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 实体类：用来和数据库一一对应！
 * 使用lombok之后不用写get set方法了就
 */

@TableName("user")  //和数据库表的名字一一对应
@Data   //使用lombok之后不用写get set方法了就
public class User {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)  // 设置自增长ID，当数据库中的ID不是id时，要把value写成那个值，当是id可写可不写
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;
}
