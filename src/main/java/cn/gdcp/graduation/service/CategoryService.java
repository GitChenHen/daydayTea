package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.Category;

import java.util.List;

public interface CategoryService {
    Object list(Long start);

    void add(Category bean);

    void delete(int id);

    Category get(int id);

    void update(Category bean);

    List<Category> listCategory();
}
