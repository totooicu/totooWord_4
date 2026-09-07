import request from "@/utils/request.js";


export function getGroupList() {
    return request({
        url: '/system/group/getGroupList',
        method: 'get',
        params: ""
    })
}