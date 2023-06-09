/*******************************************************************************
* Copyright (c) 2021 - 9999, ARES
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*        http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/

import request from '@/utils/request'

// 查询列表
export function listArticles(query) {
    return request({
    url: '/ares/system/articles/list',
    method: 'get',
    params: query
    })
}

// 查询
export function getArticles(id) {
    return request({
    url: '/ares/system/articles/' + id,
    method: 'get'
    })
}

// 新增
export function addArticles(data) {
    return request({
    url: '/ares/system/articles/edit',
    method: 'post',
    data: data
    })
}

// 修改
export function updateArticles(data) {
    return request({
    url: '/ares/system/articles/edit',
    method: 'post',
    data: data
    })
}

// 删除
export function delArticles(postId) {
    return request({
    url: '/ares/system/articles/' + postId,
    method: 'delete'
    })
}

// 导出
export function exportArticles(query) {
    return request({
    url: '/ares/system/articles/export',
    method: 'get',
    params: query
    })
}