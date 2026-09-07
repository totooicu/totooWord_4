package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunMessageList;

/**
 * 消息列Service接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IFunMessageListService 
{
    /**
     * 查询消息列
     * 
     * @param listId 消息列主键
     * @return 消息列
     */
    public FunMessageList selectFunMessageListByListId(Long listId);

    /**
     * 查询消息列列表
     * 
     * @param funMessageList 消息列
     * @return 消息列集合
     */
    public List<FunMessageList> selectFunMessageListList(FunMessageList funMessageList);

    /**
     * 新增消息列
     * 
     * @param funMessageList 消息列
     * @return 结果
     */
    public int insertFunMessageList(FunMessageList funMessageList);

    /**
     * 修改消息列
     * 
     * @param funMessageList 消息列
     * @return 结果
     */
    public int updateFunMessageList(FunMessageList funMessageList);

    /**
     * 批量删除消息列
     * 
     * @param listIds 需要删除的消息列主键集合
     * @return 结果
     */
    public int deleteFunMessageListByListIds(Long[] listIds);

    /**
     * 删除消息列信息
     * 
     * @param listId 消息列主键
     * @return 结果
     */
    public int deleteFunMessageListByListId(Long listId);
}
