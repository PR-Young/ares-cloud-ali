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

package com.ares.core.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

/**
 * @description: 公共方法
 * @author: Young 2020/05/15
 **/
public class AresCommonUtils {
    private AresCommonUtils() {
    }

    public static boolean isNotEmpty(Object obj) {
        if (obj instanceof Map) {
            return ((Map) obj).size() > 0;
        } else if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        } else if (obj instanceof String) {
            return !"".equals(((String) obj).trim());
        } else {
            return null != obj;
        }
    }

    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }


    /**
     * 回写请求数据
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void writeResponse(HttpServletResponse response, Object result) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }

    public static void writeResponse(HttpServletResponse response, Object result, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }

    /**
     * 远程请求调用
     *
     * @param uri
     * @param data
     * @param contentType
     * @return
     * @throws IOException
     */
    public static String post(String uri, String data, String contentType) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setConnectTimeout(300000);
        connection.connect();

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(data.getBytes());
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String lines;
        while (null != (lines = reader.readLine())) {
            lines = new String(lines.getBytes());
            sb.append(lines);
        }

        reader.close();
        connection.disconnect();
        return sb.toString();
    }

    public static String getUserAgent(HttpServletRequest request, String type) {
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("user-agent"));
        String result = "";
        switch (type) {
            case "browser":
                result = userAgent.getBrowser().getName();
                break;
            case "os":
                result = userAgent.getOs().getName();
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

}
