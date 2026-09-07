import {getToken} from "@/utils/auth.js";


let ws = new WebSocket("ws://localhost:8080/wsService/messageService");
function GenJsonMsg(type,message){
    return JSON.stringify({
        type: type,
        Authorization: "Bearer "+getToken(),
        message: message
    })}

ws.onopen=(e)=>{;test(); console.log("连接成功",e)}
ws.onclose=(e)=>{ console.log("断开连接",e)}
ws.onerror=(e)=>{ console.log("连接错误",e)}
export function sendMsg(msg){
    console.log(">>>sendMsg",msg)
    ws.send(GenJsonMsg("sendMsg",msg))
}
function validate(){
    ws.send(GenJsonMsg("validate",getToken()))
}
function test(){
    console.log(">>>sendTest")
    ws.send(GenJsonMsg("test",null))}

ws.onmessage=(e)=>{
    console.log(">>>receiveMsg",e)
    let type= JSON.parse(e.data).type
    let message= JSON.parse(e.data).message
    switch (type){
        case "test":console.log(">>>WebSocketTest",e.data); break;
        case "pushMsg":
            console.log(">>>WebSocketReceiveMsg",e);receiveMsg(message);break
        default:
            console.log(">>>WebSocketReceiveMsg",e);break
    }
}


export function receiveMsg(callback) {
    ws.onmessage = (e) => {
        console.log(">>>receiveMsg", e);
        let data = JSON.parse(e.data);
        let type = data.type;
        let message = data.message;

        switch (type) {
            case "test":
                console.log(">>>WebSocketTest", e.data);
                break;
            case "pushMsg":
                console.log(">>>WebSocketReceiveMsg", e);
                callback(message); // 调用回调函数并传递消息
                break;
            default:
                console.log(">>>WebSocketReceiveMsg", e);
                break;
        }
    };
}