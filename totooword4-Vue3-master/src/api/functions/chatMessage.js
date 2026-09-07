import request from "@/utils/request.js";

export function listByChatMessageBySelfUserIdOrGroupId(data){
    return request({
        url: '/system/message/listByChatMessageBySelfUserIdOrGroupId',
        method: 'post',
data: data
    })
}
export function getLastMessage(data){
    return request({
        url: '/system/message/getLastMessage',
        method: 'post',
        data: data
    })
}
export function sendMessage(data){
return request({
    url: '/system/message/sendMessage',
    method: 'post',
    data: data
})
}

export function watched(data){
return request({
    url: '/system/message/watched',
    method: 'put',
    data: data
})
}