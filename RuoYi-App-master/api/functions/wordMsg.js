import request from '@/utils/request'


export function getWordMsgBySpellLan(query) {
    return request({
        url: '/wordMsg/getWordMsgBySpellLan',
        method: 'get',
        params: query
    })
}

export function getWordMsgsBySpellsLan(query) {
    return request({
        url: '/wordMsg/getWordMsgsBySpellsLan',
        method: 'post',
        data: query
    })
}
export function getWordMsgByWordId(query) {
    return request({
        url: '/wordMsg/getWordMsgByWordId',
        method: 'get',
        params: query
    })
}
export function getWordMsgsByBookId(query) {
    return request({
        url: '/wordMsg/getWordMsgsByBookId',
        method: 'get',
        params: query
    })
}



export function ContentStatisticsByJson(query) {
    return request({
        url: '/wordMsg/ContentStatisticsByJson',
        method: 'post',
        data: query
    })
}