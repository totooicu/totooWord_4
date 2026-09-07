package com.totoo.system.service.impl;

import java.util.List;
import com.totoo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunMessageListMapper;
import com.totoo.system.domain.FunMessageList;
import com.totoo.system.service.IFunMessageListService;

/**
 * 消息列Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class FunMessageListServiceImpl implements IFunMessageListService 
{
    @Autowired
    private FunMessageListMapper funMessageListMapper;

    /**
     * 查询消息列
     * 
     * @param listId 消息列主键
     * @return 消息列
     */
    @Override
    public FunMessageList selectFunMessageListByListId(Long listId)
    {
        return funMessageListMapper.selectFunMessageListByListId(listId);
    }

    /**
     * 查询消息列列表
     * 
     * @param funMessageList 消息列
     * @return 消息列
     */
    @Override
    public List<FunMessageList> selectFunMessageListList(FunMessageList funMessageList)
    {
        return funMessageListMapper.selectFunMessageListList(funMessageList);
    }

    /**
     * 新增消息列
     * 
     * @param funMessageList 消息列
     * @return 结果
     */
    @Override
    public int insertFunMessageList(FunMessageList funMessageList)
    {
        return funMessageListMapper.insertFunMessageList(funMessageList);
    }

    /**
     * 修改消息列
     * 
     * @param funMessageList 消息列
     * @return 结果
     */
    @Override
    public int updateFunMessageList(FunMessageList funMessageList)
    {
        funMessageList.setUpdateTime(DateUtils.getNowDate());
        return funMessageListMapper.updateFunMessageList(funMessageList);
    }

    /**
     * 批量删除消息列
     * 
     * @param listIds 需要删除的消息列主键
     * @return 结果
     */
    @Override
    public int deleteFunMessageListByListIds(Long[] listIds)
    {
        return funMessageListMapper.deleteFunMessageListByListIds(listIds);
    }

    /**
     * 删除消息列信息
     * 
     * @param listId 消息列主键
     * @return 结果
     */
    @Override
    public int deleteFunMessageListByListId(Long listId)
    {
        return funMessageListMapper.deleteFunMessageListByListId(listId);
    }
}
