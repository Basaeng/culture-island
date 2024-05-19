import axios from 'axios'

const { VITE_CULTURE_API_URL } = import.meta.env

function CultureAxios() {
  const instance = axios.create({
    baseURL: VITE_CULTURE_API_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })

  // 요청 인터셉터 설정
  instance.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  }, (error) => {
    return Promise.reject(error);
  });

  return instance;
}

export { CultureAxios }
