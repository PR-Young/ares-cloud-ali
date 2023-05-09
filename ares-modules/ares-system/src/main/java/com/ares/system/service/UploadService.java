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

package com.ares.system.service;

import com.ares.core.exception.AresCommonException;
import com.ares.core.utils.DateUtils;
import com.ares.core.utils.MimeTypeUtils;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/25
 * @see: com.ares.core.persistence.service UploadService.java
 **/
@Slf4j
@Service
public class UploadService {

    private SysPropertiesService propertiesService;

    @Autowired
    public UploadService(SysPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public String upload(String path, MultipartFile file) throws Exception {
        if (null == path) {
            path = propertiesService.getValueByAlias("upload.path");
        }

        int fileNameLength = Integer.parseInt(propertiesService.getValueByAlias("file.namelength"));
        if (file.getOriginalFilename().length() > fileNameLength) {
            throw new AresCommonException("文件名最大长度为:" + fileNameLength);
        }
        assertAllowed(file);
        String user = SecurityUtils.getUser().getAccount();
        String fileOriginalName = file.getOriginalFilename();
        String fileName = user + File.separator + DateUtils.datePath() + File.separator + fileOriginalName.replace("_", "") + "." + getExtension(file);
        File desc = getAbsoluteFile(path, fileName);
        file.transferTo(desc);
        String pathName = getPathFileName(path, fileName);
        return pathName;
    }

    private File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    private String getPathFileName(String uploadDir, String fileName) throws IOException {
        String pathFileName = uploadDir + File.separator + fileName;
        return pathFileName;
    }

    private void assertAllowed(MultipartFile file) {
        long maxFileSize = Long.parseLong(propertiesService.getValueByAlias("file.maxsize"));
        String[] allow = propertiesService.getValueByAlias("file.allow").split(",");
        long size = file.getSize();
        if (maxFileSize != -1 && size > maxFileSize) {
            throw new AresCommonException("文件最大为:" + maxFileSize / 1024 / 1024 + "MB");
        }

        String extension = getExtension(file);

        if (allow != null && !isAllowedExtension(extension, allow)) {
            throw new AresCommonException("文件类型错误");
        }

    }

    private boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    private String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

}
