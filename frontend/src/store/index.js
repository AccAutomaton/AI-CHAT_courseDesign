import { createStore } from 'vuex'

export default createStore({
  state: {
    isLogin: false,
    uid: '',
    username: '',
    nickname: '',
    userType: '',
    authorization: '',
  },
  getters: {
    getIsLogin(state) {
      return state.isLogin;
    },
    getUid(state) {
      return state.uid;
    },
    getUsername(state) {
      return state.username;
    },
    getNickname(state) {
      return state.nickname;
    },
    getUserType(state) {
      return state.userType;
    },
    getAuthorization(state) {
      return state.authorization;
    }
  },
  mutations: {
    setIsLogin(state, status) {
      state.isLogin = status;
    },
    setUid(state, uid) {
      state.uid = uid;
    },
    setUsername(state, username) {
      state.username = username;
    },
    setNickname(state, nickname) {
      state.nickname = nickname;
    },
    setUserType(state, userType) {
      state.userType = userType;
    },
    setAuthorization(state, authorization) {
      state.authorization = authorization;
    }
  },
  actions: {
  },
  modules: {
  }
})
