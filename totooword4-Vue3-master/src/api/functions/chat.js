import request from '@/utils/request'; // 引入 request 函数
export function getMessageList() {
  return request({
    url: '/api/chat/messageList',
    method: 'get'
  })
}

export function getFriendList(data) {
  return request({
    url: '/api/chat/message',
    method: 'post',
    data
  })
}


export function getChatMessages(data) {
  return request({
    url: '/api/chat/chatList',
    method: 'post',
    data
  })
}