package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunGroupMember;

/**
 * 群组成员Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface FunGroupMemberMapper 
{
    /**
     * 查询群组成员
     * 
     * @param memberId 群组成员主键
     * @return 群组成员
     */
    public FunGroupMember selectFunGroupMemberByMemberId(Long memberId);

    /**
     * 查询群组成员列表
     * 
     * @param funGroupMember 群组成员
     * @return 群组成员集合
     */
    public List<FunGroupMember> selectFunGroupMemberList(FunGroupMember funGroupMember);

    /**
     * 新增群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    public int insertFunGroupMember(FunGroupMember funGroupMember);

    /**
     * 修改群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    public int updateFunGroupMember(FunGroupMember funGroupMember);

    /**
     * 删除群组成员
     * 
     * @param memberId 群组成员主键
     * @return 结果
     */
    public int deleteFunGroupMemberByMemberId(Long memberId);

    /**
     * 批量删除群组成员
     * 
     * @param memberIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunGroupMemberByMemberIds(Long[] memberIds);
    public List<FunGroupMember> witchMemberIsInGroupByUserId(Long userId);
    public List<FunGroupMember> witchMemberIsInGroupByGroupId(Long groupId);
}
