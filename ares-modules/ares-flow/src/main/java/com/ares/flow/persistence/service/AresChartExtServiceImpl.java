/*
 *
 *  * ****************************************************************************
 *  * * Copyright (c) 2021 - 9999, ARES
 *  * *
 *  * * Licensed under the Apache License, Version 2.0 (the "License");
 *  * * you may not use this file except in compliance with the License.
 *  * * You may obtain a copy of the License at
 *  * *
 *  * *        http://www.apache.org/licenses/LICENSE-2.0
 *  * *
 *  * * Unless required by applicable law or agreed to in writing, software
 *  * * distributed under the License is distributed on an "AS IS" BASIS,
 *  * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * * See the License for the specific language governing permissions and
 *  * * limitations under the License.
 *  * ***************************************************************************
 *
 */

package com.ares.flow.persistence.service;


import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.PromptContent;
import org.dromara.warm.flow.core.utils.MapUtil;
import org.dromara.warm.flow.ui.service.ChartExtService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2025/6/20
 * @see: com.ares.flow.persistence.service.impl.AresChartExtServiceImpl.java
 **/
@Service
public class AresChartExtServiceImpl implements ChartExtService {
    @Override
    public void execute(DefJson defJson) {
        defJson.setTopText(defJson.getFlowName());
        defJson.getNodeList().forEach(nodeJson -> {
            // extMap是在分派监听器中设置的, 用户使用的时候不用局限于这种方式, 可以临时查询出来, 或者通过其他方式获取提示信息
            Map<String, Object> extMap = nodeJson.getExtMap();
            if (MapUtil.isNotEmpty(extMap)) {
                for (Map.Entry<String, Object> entry : extMap.entrySet()) {
                    // 添加第二个条目
                    PromptContent.InfoItem item2 = new PromptContent.InfoItem();
                    item2.setPrefix(entry.getKey() + ": ");
                    item2.setContent((String) entry.getValue());
                    nodeJson.getPromptContent().getInfo().add(item2);
                }
            }
        });
    }
}
