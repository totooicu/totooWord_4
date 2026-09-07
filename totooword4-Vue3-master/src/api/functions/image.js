
export function getImage(url){//若为http开头则直接返回，若为/开头则加上域名
    if(url==null||url===" ")return "";
    if(url.indexOf("http")===0)return url;
    // if(url[0]==="/")return process.env.VUE_APP_BASE_API+url;
}