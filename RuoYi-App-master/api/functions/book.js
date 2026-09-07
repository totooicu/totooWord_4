
import request from '@/utils/request'; // 引入 request 函数

export function getBookByOwnWordIdOrLan(query) {
    return request({
        url: '/system/book/getBookByOwnWordIdOrLan',
        method: 'get',
        params: query
    })
}
export function getBookByOwn() {
    return request({
        url: '/system/book/getBookByOwn',
        method: 'get',
        params: ""
    })
}


// 查询书本信息列表
export function listBook(query) {
    return request({
        url: '/system/book/list',
        method: 'get',
        params: query
    })
}
// 查询书本信息列表
export function listBySelf(query) {
    return request({
        url: '/system/book/listBySelf',
        method: 'get',
        params: query
    })
}
// 查询书本信息列表
export function listByUserBookBySelf(query) {
    return request({
        url: '/system/book/listByUserBookBySelf',
        method: 'get',
        params: query
    })
}
// 查询书本信息详细
export function getBook(bookId) {
    return request({
        url: '/system/book/' + bookId,
        method: 'get'
    })
}

// 新增书本信息
export function addBook(data) {
    return request({
        url: '/system/book',
        method: 'post',
        data: data
    })
}

// 修改书本信息
export function updateBook(data) {
    return request({
        url: '/system/book',
        method: 'put',
        data: data
    })
}

// 删除书本信息
export function delBook(bookId) {
    return request({
        url: '/system/book/' + bookId,
        method: 'delete'
    })
}
// 修改书本信息
export function BooksStatisticsByBookIds(data) {
    return request({
        url: '/system/book/BooksStatisticsByBookIds',
        method: 'post',
        data: data
    })
}

export function listByBookSelf(data) {
    return request({
        url: '/system/book/listByBookSelf',
        method: 'post',
        data: data
    })
}
