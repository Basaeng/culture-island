import { Axios } from '@/util/http-common'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const token = ref(localStorage.getItem('jwt') || '')
const axios = Axios()

export function useAuthStore() {
  const router = useRouter()

  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('jwt', newToken)
  }

  const clearToken = () => {
    token.value = ''
    localStorage.removeItem('jwt')
  }

  const isAuthenticated = computed(() => {
    console.log('Token value:', token.value) // 디버깅용 로그 추가
    return !!token.value // 반환 값 추가
  })

  return {
    token,
    setToken,
    clearToken,
    isAuthenticated,
  }
}
