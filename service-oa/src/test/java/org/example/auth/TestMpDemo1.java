package org.example.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.auth.mapper.SysRoleMapper;
import org.example.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMpDemo1 {

    @Autowired
    private SysRoleMapper mapper;

    @Test
    public void getAll() {
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }

    @Test
    public void add() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");

        int rows = mapper.insert(sysRole);
        System.out.println(rows);
        System.out.println(sysRole);
    }

    @Test
    public void update() {
        SysRole role = mapper.selectById(9);
        role.setRoleName("newname");
        int rows = mapper.updateById(role);
        System.out.println(role);
        System.out.println(rows);
    }

    @Test
    public void delete() {
        int rows = mapper.deleteById(9);
    }

    @Test
    public void testQuery1() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", "gm");
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }

    @Test
    public void testQuery2() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole:: getRoleName, "admin");
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }
}
