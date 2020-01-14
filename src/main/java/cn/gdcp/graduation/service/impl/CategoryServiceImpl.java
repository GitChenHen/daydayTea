package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.CategoryMapper;
import cn.gdcp.graduation.pojo.Category;
import cn.gdcp.graduation.service.CategoryService;
import cn.gdcp.graduation.utils.page.PageResponse;
import cn.gdcp.graduation.utils.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Object list(Long start) {
        Long count = categoryMapper.selectListCount();
        PageUtils pageUtils = new PageUtils();
        pageUtils.init(count,start);
        return new PageResponse(start,5l,pageUtils.getTotal(),categoryMapper.selectList(pageUtils.getIndex()));
    }

    @Override
    public void add(Category bean) {
        categoryMapper.insertSelective(bean);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category bean) {
        categoryMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public List<Category> listCategory() {
        return categoryMapper.selectAll();
    }
}
