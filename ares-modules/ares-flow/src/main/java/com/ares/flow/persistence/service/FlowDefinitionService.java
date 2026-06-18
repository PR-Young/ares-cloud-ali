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

package com.ares.flow.persistence.service;


import com.ares.flow.model.chart.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.DefChart;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.NodeJson;
import org.dromara.warm.flow.core.dto.SkipJson;
import org.dromara.warm.flow.core.enums.ChartStatus;
import org.dromara.warm.flow.core.enums.NodeType;
import org.dromara.warm.flow.core.exception.FlowException;
import org.dromara.warm.flow.core.utils.Base64;
import org.dromara.warm.flow.core.utils.CollUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlowDefinitionService {

    public String getFlowChart(Long definitionId) {
        DefJson defJson = FlowEngine.defService().queryDesign(definitionId);
        initStatus(defJson);
        DefChart flowChart = copyChart(defJson);
        return basicFlowChart(flowChart.getNodeJsonList(), flowChart.getSkipJsonList(), defJson.getModelValue());
    }

    public static DefChart copyChart(DefJson defJson) {
        DefChart defChart = new DefChart();
        defChart.setDefJson(defJson);
        defChart.setNodeJsonList(defJson.getNodeList());
        defChart.setSkipJsonList(Optional.of(defJson).map(DefJson::getNodeList).orElse(Collections.emptyList()).stream().map(NodeJson::getSkipList).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList()));
        return defChart;
    }

    private void initStatus(DefJson defJson) {
        List<NodeJson> nodeList = defJson.getNodeList();
        List<SkipJson> skipList = nodeList.stream().map(NodeJson::getSkipList).flatMap(List::stream)
                .collect(Collectors.toList());
        nodeList.forEach(node -> node.setStatus(ChartStatus.NOT_DONE.getKey()));
        skipList.forEach(skip -> skip.setStatus(ChartStatus.NOT_DONE.getKey()));
    }

    /**
     * DefService 根据流程实例ID获取流程图的图片流(渲染颜色)
     *
     * @param nodeJsonList 流程节点对象Vo
     * @param skipJsonList 节点跳转关联对象Vo
     * @return 流程图base64字符串
     */
    private String basicFlowChart(List<NodeJson> nodeJsonList, List<SkipJson> skipJsonList, String modelValue) {
        try {

            Map<String, Integer> chartXY = new HashMap<>();
            chartXY.put("minX", 5000);
            chartXY.put("minY", 5000);
            chartXY.put("maxX", 0);
            chartXY.put("maxY", 0);

            FlowChartChain flowChartChain = new FlowChartChain();
            addNodeChart(chartXY, nodeJsonList, flowChartChain);
            addSkipChart(chartXY, skipJsonList, flowChartChain);

            // 清晰度
            int n = 2;
            int offset = 100;
            int offsetW = 0;
            int offsetH = 0;
            // 如果有坐标小于0，则设置偏移量
            if (chartXY.get("minX") < 0) {
                offsetW = offset - chartXY.get("minX");
                chartXY.put("maxX", chartXY.get("maxX") + offsetW);
                chartXY.put("minX", 0);
            }
            if (chartXY.get("minY") < 0) {
                offsetH = offset - chartXY.get("minY");
                chartXY.put("maxY", chartXY.get("maxY") + offsetH);
                chartXY.put("minY", 0);
            }
            int width = (chartXY.get("maxX") + chartXY.get("minX")) * n + offset;

            int height = (chartXY.get("maxY") + chartXY.get("minY")) * n + offset;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取图形上下文,graphics想象成一个画笔
            Graphics2D graphics = image.createGraphics();
            graphics.setStroke(new BasicStroke((2 * n) + 1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            Font font = new Font("宋体", Font.BOLD, 12 * n);
            graphics.setFont(font);
            // 消除线条锯齿
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // 对指定的矩形区域填充颜色: GREEN:绿色；  红色：RED;   灰色：GRAY
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);

            flowChartChain.draw(width, height, offsetW, offsetH, graphics, n, modelValue);

            graphics.setPaintMode();
            // 释放此图形的上下文并释放它所使用的所有系统资源
            graphics.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            return Base64.encode(os.toByteArray());
        } catch (IOException e) {
            log.error("获取流程图异常", e);
            throw new FlowException("获取流程图异常");
        }

    }

    /**
     * 添加节点流程图
     *
     * @param chartXY        流程图坐标边界
     * @param nodeJsonList   流程节点对象Vo
     * @param flowChartChain 流程图链
     */
    private void addNodeChart(Map<String, Integer> chartXY, List<NodeJson> nodeJsonList, FlowChartChain flowChartChain) {
        for (NodeJson nodeJson : nodeJsonList) {
            if (StringUtils.isNotEmpty(nodeJson.getCoordinate())) {
                String[] coordinateSplit = nodeJson.getCoordinate().split("\\|");
                String[] nodeSplit = coordinateSplit[0].split(",");
                int nodeX = Integer.parseInt(nodeSplit[0].split("\\.")[0]);
                int nodeY = Integer.parseInt(nodeSplit[1].split("\\.")[0]);
                setChartXy(chartXY, nodeX, nodeY);
                TextChart textChart = null;
                if (coordinateSplit.length > 1) {
                    String[] textSplit = coordinateSplit[1].split(",");
                    int textX = Integer.parseInt(textSplit[0].split("\\.")[0]);
                    int textY = Integer.parseInt(textSplit[1].split("\\.")[0]);
                    if (NodeType.isBetween(nodeJson.getNodeType())) {
                        textChart = new TextChart(nodeJson.getNodeName());
                    } else {
                        textChart = new TextChart(textX, textY, nodeJson.getNodeName());
                    }
                }
                Color c = ChartStatus.getColorByKey(nodeJson.getStatus());
                if (NodeType.isStart(nodeJson.getNodeType())) {
                    flowChartChain.addFlowChart(new OvalChart(nodeX, nodeY, c, textChart, nodeJson));
                } else if (NodeType.isBetween(nodeJson.getNodeType())) {
                    flowChartChain.addFlowChart(new BetweenChart(nodeX, nodeY, c, CollUtil.toList(textChart), nodeJson));
                } else if (NodeType.isGateWaySerial(nodeJson.getNodeType())) {
                    flowChartChain.addFlowChart(new SerialChart(nodeX, nodeY, c, textChart, nodeJson));
                } else if (NodeType.isGateWayParallel(nodeJson.getNodeType())) {
                    flowChartChain.addFlowChart(new ParallelChart(nodeX, nodeY, c, nodeJson));
                } else if (NodeType.isEnd(nodeJson.getNodeType())) {
                    flowChartChain.addFlowChart(new OvalChart(nodeX, nodeY, c, textChart, nodeJson));
                }
            }
        }
    }

    private static void setChartXy(Map<String, Integer> chartXY, int nodeX, int nodeY) {
        if (nodeX > chartXY.get("maxX")) {
            chartXY.put("maxX", nodeX);
        }
        if (nodeX < chartXY.get("minX")) {
            chartXY.put("minX", nodeX);
        }
        if (nodeY > chartXY.get("maxY")) {
            chartXY.put("maxY", nodeY);
        }
        if (nodeY < chartXY.get("minY")) {
            chartXY.put("minY", nodeY);
        }
    }

    /**
     * 添加跳转流程图
     *
     * @param chartXY        流程图坐标边界
     * @param skipJsonList   节点跳转关联对象Vo
     * @param flowChartChain 流程图链
     */
    private void addSkipChart(Map<String, Integer> chartXY, List<SkipJson> skipJsonList, FlowChartChain flowChartChain) {
        for (SkipJson skipJson : skipJsonList) {
            if (StringUtils.isNotEmpty(skipJson.getCoordinate())) {
                String[] coordinateSplit = skipJson.getCoordinate().split("\\|");

                TextChart textChart = null;
                if (coordinateSplit.length > 1) {
                    String[] textSplit = coordinateSplit[1].split(",");
                    int textX = Integer.parseInt(textSplit[0].split("\\.")[0]);
                    int textY = Integer.parseInt(textSplit[1].split("\\.")[0]);
                    textChart = new TextChart(textX, textY, skipJson.getSkipName());
                }

                String[] skipSplit = coordinateSplit[0].split(";");
                int[] skipX = new int[skipSplit.length];
                int[] skipY = new int[skipSplit.length];
                for (int i = 0; i < skipSplit.length; i++) {
                    skipX[i] = Integer.parseInt(skipSplit[i].split(",")[0].split("\\.")[0]);
                    skipY[i] = Integer.parseInt(skipSplit[i].split(",")[1].split("\\.")[0]);
                    setChartXy(chartXY, skipX[i], skipY[i]);
                }
                Color c = ChartStatus.getColorByKey(skipJson.getStatus());
                flowChartChain.addFlowChart(new SkipChart(skipX, skipY, c, textChart));
            }
        }
    }

}
