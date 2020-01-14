package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.PropertyValueMapper;
import cn.gdcp.graduation.pojo.PropertyValue;
import cn.gdcp.graduation.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Override
    public void update(PropertyValue bean) {
        propertyValueMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public PropertyValue get(int pid) {
        return propertyValueMapper.get(pid);
    }

    @Override
    public void insert(PropertyValue propertyValue) {
        propertyValueMapper.insertSelective(propertyValue);
    }
}
