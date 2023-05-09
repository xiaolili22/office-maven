package org.example.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.model.process.ProcessTemplate;
import org.example.model.process.ProcessType;
import org.example.process.mapper.OaProcessTypeMapper;
import org.example.process.service.OaProcessTemplateService;
import org.example.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author lili
 * @since 2023-05-08
 */
@Service
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType> implements OaProcessTypeService {

    @Autowired
    private OaProcessTemplateService processTemplateService;

    @Override
    public List<ProcessType> findProcessType() {
        List<ProcessType> processTypeList = baseMapper.selectList(null);
        for (ProcessType processType : processTypeList) {
            Long typeId = processType.getId();
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId, typeId);
            List<ProcessTemplate> processTemplateList = processTemplateService.list(wrapper);
            processType.setProcessTemplateList(processTemplateList);
        }
        return processTypeList;
    }
}
