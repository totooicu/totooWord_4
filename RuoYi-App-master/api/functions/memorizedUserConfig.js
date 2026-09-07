import request from '@/utils/request'; // 引入 request 函数


export function getBySelf() {
    return request({
        url: '/system/memorizedUserConfig/getBySelf',
        method: 'get',
    })
}
export function edit(data){
    return request({
        url: '/system/memorizedUserConfig',
        method: 'put',
        data: data
    })
}