package org.example.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.example.auth.service.SysMenuService;
import org.example.auth.service.SysUserService;
import org.example.common.config.CustomException;
import org.example.common.jwt.JwtHelper;
import org.example.common.result.Result;
import org.example.common.utils.MD5;
import org.example.model.system.SysUser;
import org.example.vo.system.LoginVo;
import org.example.vo.system.RouterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "后台登陆管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    // Login
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", "admin-token");
//        return Result.ok(map);
        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(wrapper);

        if (sysUser == null) {
            throw new CustomException(201, "用户不存在");
        }

        String password_db = sysUser.getPassword();
        String password_input = MD5.encrypt(loginVo.getPassword());
        if (!password_input.equals(password_db)) {
            throw new CustomException(201, "密码错误");
        }

        if (sysUser.getStatus().intValue() == 0) {
            throw new CustomException(201, "用户已被禁用");
        }

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    // Info
    @GetMapping("info")
    public Result info(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        SysUser sysUser = sysUserService.getById(userId);
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(userId);
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");

        map.put("routers", routerVoList);
        map.put("buttons", permsList);

        return Result.ok(map);
    }

    // Logout
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }
}
