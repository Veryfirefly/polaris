import storage from 'store'
import { login, getInfo, logout } from '@/api/login'
import { AUTHENTICATION } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    authentication: {},
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {},
    tenantId: ''
  },

  mutations: {
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_AUTHENTICATION: (state, authentication) => {
      state.authentication = authentication
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const { status, data, message } = response
          if (status === 200 && data) {
            const { token, timeToLiveMs, tenantId } = data

            const authentication = { token, tenantId }
            storage.set(AUTHENTICATION, authentication, timeToLiveMs)
            commit('SET_AUTHENTICATION', authentication)

            resolve()
          } else {
            reject(new Error(message))
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const { status, data, message } = response

          if (status === 200 && data && data.permissions) {
            commit('SET_ROLES', data.permissions)
            commit('SET_INFO', data)

            commit('SET_NAME', { name: data.nickname, welcome: welcome() })
            commit('SET_AVATAR', data.avatar)

            resolve(response)
          } else {
            reject(new Error(message))
          }
          // reject(new Error('getInfo: roles must be a non-null array !'))
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_AUTHENTICATION', {})
          commit('SET_ROLES', [])
          commit('SET_ROUTERS', [])
          commit('SET_INFO', {})
          commit('SET_NAME', '')
          commit('SET_AVATAR', '')
          storage.remove(AUTHENTICATION)
        })
      })
    }

  }
}

export default user
