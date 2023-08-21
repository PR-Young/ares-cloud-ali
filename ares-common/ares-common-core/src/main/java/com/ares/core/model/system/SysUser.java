/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares.core.model.system;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ares.core.model.base.BaseModel;
import com.ares.core.serializer.LongJsonDeserializer;
import com.ares.core.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
@Schema(name = "SysUser对象", description = "系统用户")
public class SysUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1335150848291052716L;
    @Schema(description = "帐号")
    @ExcelProperty(value = "帐号", index = 1)
    private String account;
    @Schema(description = "用户名")
    @ExcelProperty(value = "用户名", index = 2)
    private String userName;
    @Schema(description = "密码")
    @ExcelIgnore
    private String password;
    @Schema(description = "电话")
    @ExcelProperty(value = "电话", index = 3)
    private String phoneNumber;
    @Schema(description = "邮箱")
    @ExcelProperty(value = "邮箱", index = 4)
    private String email;
    @Schema(description = "头像")
    @ExcelIgnore
    private String avatar;
    @ExcelIgnore
    private Long[] roleIds;
    @ExcelIgnore
    private boolean isAdmin;
    @ExcelIgnore
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long deptId;
    @ExcelIgnore
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long postId;

    public SysUser() {
    }

    public boolean isAdmin() {
        return isAdmin(getId());
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static boolean isAdmin(Long userId) {
        return userId == 1;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
