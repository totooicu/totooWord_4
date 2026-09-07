import request from '@/utils/request'; // 引入 request 函数

export function getMessageList(){
    return request({
        url: '/system/messageList/getMessageList',
        method: 'get'
    })
}