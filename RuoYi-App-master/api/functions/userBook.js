import request from '@/utils/request'; // 引入 request 函数
export function editByBookIdsWordIdSelf(query) {
    return request({
        url: '/system/userbook/editByBookIdsSelf',
        method: 'post',
        data: query
    })
}