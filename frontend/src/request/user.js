// noinspection JSIgnoredPromiseFromCall

import axiosService from "@/request/index";
import {useStorage} from "@vueuse/core";
import store from "@/store";
import {ElMessageBox, ElNotification} from "element-plus";
import router from "@/router";

const authorization = useStorage("Authorization", "");

export const Login = async (username, password) => {
    axiosService({
        url: "/login",
        method: "post",
        data: {
            "username": username.value,
            "password": password.value
        }
    }).then(data => {
        if (data != null) {
            authorization.value = data["Authorization"];
            store.commit("setIsLogin", true);
            store.commit("setAuthorization", data["Authorization"]);
            ElNotification({
                "title": "登录成功",
                "type": "success"
            });
            router.push("/");
        }
    })
}

export const Register = async (username, password) => {
    axiosService({
        url: "/register",
        method: "post",
        data: {
            "username": username.value,
            "password": password.value,
        }
    }).then(data => {
        if (data["status"] === "success") {
            ElMessageBox.alert('即将前往登录页面', '注册成功', {
                confirmButtonText: '确认',
                callback: () => {
                    router.push("/login")
                },
            })
        }
    })
}

export const GetRoughUserInformation = async () => {
    if (authorization.value === "" && store.getters.getAuthorization === "") {
        return;
    }
    axiosService({
        url: "/user/get/roughInformation",
        method: "get"
    }).then(data => {
        store.commit("setUid", data["uid"]);
        store.commit("setUsername", data["username"]);
        store.commit("setNickname", data["nickname"]);
        store.commit("setUserType", data["userType"]);
        store.commit("setIsLogin", true);
    })
}

export const GetUserInformation = async () => {
    axiosService({
        url: "/user/get/Information",
        method: "get"
    }).then(data => {
        return data;
    });
}

export const Logout = () => {
  authorization.value = "";
  store.commit("setIsLogin", false);
  store.commit("setAuthorization", "");
  router.push("/login");
}