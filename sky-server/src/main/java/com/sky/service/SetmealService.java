package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐并关联菜品
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);


    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐详情
     * @param id
     * @return
     */
    SetmealVO getById(Long id);


    /**
     * 修改套餐并关联菜品
     * @param setmealDTO
     */
    void updateWithDish(SetmealDTO setmealDTO);


    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteSetmeal(List<Long> ids);

    /**
     * 套餐起售停售
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);
}
