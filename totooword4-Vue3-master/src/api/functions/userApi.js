import request from '@/utils/request'; // 引入 request 函数
export function getUserById(id) {
    return request({
        url: '/system/user/getUserById/'+id,
        method:"get",
    })
}