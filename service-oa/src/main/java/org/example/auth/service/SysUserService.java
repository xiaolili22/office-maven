package org.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lili
 * @since 2023-05-03
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);

    SysUser getUserByUserName(String username);
}
