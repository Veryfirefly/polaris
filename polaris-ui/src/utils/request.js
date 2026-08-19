import axios from 'axios'
import store from '@/store'
import storage from 'store'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import { AUTHENTICATION } from '@/store/mutation-types'

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 6000 // 请求超时时间
})

// 异常拦截处理器
const errorHandler = (error) => {
  if (error.response) {
    const data = error.response.data
    // 从 localstorage 获取 token
    const auth = storage.get(AUTHENTICATION)
    // 未授权
    if (error.response.status === 403) {
      notification.error({
        message: '禁止访问',
        description: data.message || '您无权访问该资源'
      })
      return Promise.reject(new Error(data.message))
    }

    // 未认证
    if (error.response.status === 401) {
      notification.error({
        message: '未认证',
        description: data.message || '您还未授权'
      })
      if (auth && Object.keys(auth).length > 0) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
      return Promise.reject(new Error(data.message))
    }
  }
  // other error
  return Promise.reject(error)
}

// request interceptor
request.interceptors.request.use(config => {
  const auth = storage.get(AUTHENTICATION)
  // 如果 token 存在
  // 让每个请求携带自定义 token 请根据实际情况自行修改
  if (auth && Object.keys(auth).length > 0) {
    config.headers['Authorization'] = `Bearer ${auth.token}`
    config.headers['X-Tenant-Id'] = auth.tenantId
  }
  return config
}, errorHandler)

// response interceptor
request.interceptors.response.use((response) => {
  return response.data
}, errorHandler)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios
}
