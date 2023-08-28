import request from '@/utils/request'

// 查询登录日志列表
export function list(query) {
  return request({
    url: '/ares/system/sysLoginInfo/list',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function delLogininfor(infoId) {
  return request({
    url: '/ares/system/sysLoginInfo/' + infoId,
    method: 'delete'
  })
}

// 清空登录日志
export function cleanLogininfor() {
  return request({
    url: '/ares/system/sysLoginInfo/clean',
    method: 'delete'
  })
}

// 导出登录日志
export function exportLogininfor(query) {
  return request({
    url: '/ares/system/sysLoginInfo/export',
    method: 'get',
    params: query
  })
}
