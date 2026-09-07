import request from '@/utils/request'; // 引入 request 函数
export function listBySelf() {
    return request({
        url: '/system/friend/listBySelf',
        method: 'get',
        params: ""
    })
}
export function getFriendList() {
    return request({
        url: '/system/friend/getFriendList',
        method: 'get',
        params: ""
    })
}
export function getBySelfAndOtherUserId(userId) {
    return request({
        url: '/system/friend/getBySelfAndOtherUserId',
        method: 'get',
        params: {
            userId: userId
        }
    })
}
export function addFriend(userId) {
    return request({
        url: '/system/friend',
        method: 'post',
        data: {
            userId2: userId
        }
    })
}
export function deleteFriend(userId) {
    return request({
        url: '/system/friend/'+userId,
        method: 'delete'
    })
}
export function agreeFriend(userId1){
    return request({
        url: '/system/friend/agreeFriend',
        method: 'post',
        data: {
            userId1: userId1
        }
    })
}
export function refuseFriend(userId1){
    return request({
        url: '/system/friend/refuseFriend',
        method: 'post',
        data: {
            userId1: userId1
        }
    })
}