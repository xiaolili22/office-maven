package org.example.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.example.model.process.Process;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author lili
 * @since 2023-05-09
 */
public interface OaProcessMapper extends BaseMapper<Process> {

    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, @Param("vo") ProcessQueryVo processQueryVo);

}
