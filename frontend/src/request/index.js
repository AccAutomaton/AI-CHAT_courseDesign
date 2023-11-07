// noinspection JSIgnoredPromiseFromCall

import axios from "axios";
import {ElNotification} from "element-plus";
import router from "@/router";
import {useStorage} from "@vueuse/core";
import store from "@/store";

const authorization = useStorage("Authorization", "");

export const GetAuthorization = () => {
    if (store.getters.getAuthorization !== "") {
        return store.getters.getAuthorization;
    }
    store.commit("setAuthorization", authorization.value);
    return authorization.value;
}

const axiosService = axios.create({
    baseURL: process.env["VUE_APP_API_HOST"],
    timeout: 60000,
    headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest"
    }
})

axiosService.interceptors.request.use(
    function (config) {
        config.headers["Authorization"] = GetAuthorization();
        return config;
    },
    function (error) {
        ElNotification({
            title: '客户端错误',
            message: '向服务器请求数据时发生异常',
            type: 'error'
        });
        return Promise.reject(error)
    }
)

axiosService.interceptors.response.use(
    function (response) {
        if (response.status === 403) {
            ElNotification({
                title: '错误',
                message: '未授权的操作 | 服务器错误',
                type: 'error'
            })
            return null;
        }
        let status = response.data["status"];
        if (status === "error") {
            ElNotification({
                title: '错误',
                message: response.data["message"],
                type: 'warning'
            });
            return null;
        }
        else if (status === "jwtError") {
            ElNotification({
                title: '错误',
                message: '登录已失效，请重新登录\n' + response.data["message"],
                type: 'warning'
            })
            authorization.value = "";
            store.commit("setAuthorization", "");
            router.push('/login');
            return null;
        }
        else if (status === "success") {
            return response.data["data"];
        }
        else {
            ElNotification({
                title: '未知响应',
                message: '无法解析服务器的响应',
                type: 'info'
            })
            return null;
        }
    },
    function (error) {
      ElNotification({
          title: '服务器错误',
          message: '服务器发生内部错误',
          type: 'error'
      });
      return Promise.reject(error)
    }
)

export default axiosService