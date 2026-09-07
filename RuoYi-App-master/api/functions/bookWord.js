import request from '@/utils/request'; // 引入 request 函数


export function searchBookIdsByWordIdSelf(query) {
    return request({
        url: '/system/bookword/searchBookIdsByWordIdSelf',
        method: 'get',
        params: query
    })
}

export function addWord2Book(query) {
    return request({
        url: '/system/bookword',
        method: 'post',
        data: query
    })
}
export function add(query) {
    return request({
        url: '/system/bookword',
        method: 'post',
        data: query
    })
}
export function addsByBookIdWordIds(query) {
    return request({
        url: '/system/bookword/addsByBookIdWordIds',
        method: 'post',
        data: query
    })
}
export function addsByBookWordsByBookIdWordId(query) {
    return request({
        url: '/system/bookword/addsByBookWordsByBookIdWordId',
        method: 'post',
        data: query
    })
}

export function addsByBookWordsByBookIds_ByLogical(data) {
    return request({
        url: '/system/bookword/addsByBookWordsByBookIds_ByLogical',
        method: 'post',
        data: data
    })
}

export function editByBookIdsWordIdSelf(query) {
    return request({
        url: '/system/bookword/editByBookIdsWordIdSelf',
        method: 'post',
        data: query
    })
}