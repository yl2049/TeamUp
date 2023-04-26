import axios from 'axios'
import router from "@/router";
import Cookies from 'js-cookie';

const request = axios.create({
    baseURL: 'http://35.224.104.100:9090',  //后台地址 不要重复写！
    // baseURL: '/api',  //后台地址 不要重复写！
    timeout: 5000
})

// request Intercepter
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token;  // 设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

// response Intercepter
request.interceptors.response.use(
    response => {
        let res = response.data //res:{code:, data:, msg:}
        // console.log('response' + res)
        //
        if (typeof res === 'string') {
            // console.log(res)
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request