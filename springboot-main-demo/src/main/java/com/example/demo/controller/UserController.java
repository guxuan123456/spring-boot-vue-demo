package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")

public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){ //前台传过来json对象可以映射成Java实体，就是这个user对象
        User user1 = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if(user1 == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success();
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){ //前台传过来json对象可以映射成Java实体，就是这个user对象
        User user1 = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if(user1 != null){
            return Result.error("-1","用户名重复");
        }
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping    //插入
    public Result<?> save(@RequestBody User user){ //前台传过来json对象可以映射成Java实体，就是这个user对象
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping //更新
    public Result<?> update(@RequestBody User user){ //前台传过来json对象可以映射成Java实体，就是这个user对象
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id){ //前台传过来json对象可以映射成Java实体，就是这个user对象
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping //查询
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        // 这样写的问题是会对search进行匹配，这样的话nickName为NULL的时候的数据是找不到的。改进思路是判断传入的search是否有值，有值
        // 进行like匹配。不然查询所有的数据
//        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<User>lambdaQuery().like(User::getNickName, search));
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

}
