import request from "@/utils/request.js";

export function getBySelfGroupId(groupId) {
  return request({
    url: '/system/member/getBySelfGroupId',
 method: 'get',
    params: { groupId }
  })
}

export function addMember(groupId) {
  return request({
    url: '/system/member',
    method: 'post',
    data: { groupId }
  })
}

export function handleRequest(data) {
  return request({
	  url: '/system/member/handleRequest',
    method: 'put',
    data
  })
}
export function deleteMember(memberId) {
  return request({
    url: '/system/member/'+memberId,
    method: 'delete',
  })
}
export function listByGroupId(groupId){
  return request({
    url: '/system/member/listByGroupId',
    method: 'get',
    params: { groupId }
  })
}