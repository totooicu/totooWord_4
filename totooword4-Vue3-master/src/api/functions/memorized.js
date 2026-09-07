import request from '@/utils/request'

// 查询用户学习配置列表
export function listConfig(query) {
    return request({
        url: '/system/memorized/list',
        method: 'get',
        params: query
    })
}

// 查询用户学习配置详细
export function getConfig(userId) {
    return request({
        url: '/system/memorized/' + userId,
        method: 'get'
    })
}

// 新增用户学习配置
export function addConfig(data) {
    return request({
        url: '/system/memorized',
        method: 'post',
        data: data
    })
}

// 修改用户学习配置
export function updateConfig(data) {
    return request({
        url: '/system/memorized',
        method: 'put',
        data: data
    })
}

// 删除用户学习配置
export function delConfig(userId) {
    return request({
        url: '/system/memorized/' + userId,
        method: 'delete'
    })
}
export function listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId(params){
    return request({
        url: '/system/memorized/listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId',
        method: 'get',
        params: params
    })
}
export function getMemorizedDayBySelf(){
    return request({
        url: '/system/memorized/getMemorizedDayBySelf',
        method: 'get',
    })
}
export function getTodayReviewWordBySelf(){
    return request({
        url: '/system/memorized/getTodayReviewWordBySelf',
        method: 'get',
    })
}
export function getTodayNewWordBySelf(){
    return request({
        url: '/system/memorized/getTodayNewWordBySelf',
        method: 'get',
    })
}
export function updataStudayDataBySelfWordId(data){
    return request({
        url: '/system/memorized/updataStudayDataBySelfWordId',
        method: 'put',
        data: data
    })
}