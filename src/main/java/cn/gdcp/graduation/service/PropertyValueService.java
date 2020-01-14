package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.PropertyValue;

public interface PropertyValueService {
    void update(PropertyValue bean);

    PropertyValue get(int pid);

    void insert(PropertyValue propertyValue);
}
