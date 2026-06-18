import request from '@/utils/request'

// 查询岗位列表
export function listPost(query) {
  return request({
    url: '/ares/system/sysPost/list',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
export function getPost(postId) {
  return request({
    url: '/ares/system/sysPost/' + postId,
    method: 'get'
  })
}

// 新增岗位
export function addPost(data) {
  return request({
    url: '/ares/system/sysPost/edit',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updatePost(data) {
  return request({
    url: '/ares/system/sysPost/edit',
    method: 'post',
    data: data
  })
}

// 删除岗位
export function delPost(postId) {
  return request({
    url: '/ares/system/sysPost/' + postId,
    method: 'delete'
  })
}

// 导出岗位
export function exportPost(query) {
  return request({
    url: '/ares/system/system/post/export',
    method: 'get',
    params: query
  })
}
