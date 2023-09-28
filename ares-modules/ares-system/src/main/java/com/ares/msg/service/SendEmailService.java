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

package com.ares.msg.service;

import com.ares.core.model.base.DefaultMessage;
import com.ares.core.model.system.SysTemplate;
import com.ares.core.utils.SpringUtils;
import com.ares.system.dao.ISysTemplateDao;
import com.ares.system.service.SysPropertiesService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.annotation.Resource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Young 2020/06/08
 **/
@Service
public class SendEmailService {

    @Resource
    private ISysTemplateDao sysTemplateDao;

    public void sendEmail() {
        try {
            SysTemplate template = sysTemplateDao.getByName("test");
            List<String> receivers = Arrays.asList(new String[]{"2862322640@qq.com"});
            Map<String, Object> var = new HashMap<>(10);
            var.put("name", "这是测试");

            DefaultMessage message = DefaultMessage.newInstance()
                    .sender("2862322640@qq.com")
                    .receivers(receivers)
                    .vars(var)
                    .template(template)
                    .build();
            send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String template, List<String> receivers, Map<String, Object> vars) {
        try {
            SysTemplate sysTemplate = sysTemplateDao.getByName(template);
            SysPropertiesService propertiesService = SpringUtils.getBean(SysPropertiesService.class);
            String sender = propertiesService.getValueByAlias("mail.username");
            DefaultMessage message = DefaultMessage.newInstance()
                    .sender(sender)
                    .receivers(receivers)
                    .vars(vars)
                    .template(sysTemplate)
                    .build();
            send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(DefaultMessage msg) throws Exception {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(msg.getSender());
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(msg.getReceiver().stream().collect(Collectors.joining(",")).toString()));
        message.setSubject(msg.getTemplate().getSubject());
        StringWriter writer = new StringWriter();
        Template template = new Template("template", new StringReader(msg.getTemplate().getHtml()), new Configuration(Configuration.VERSION_2_3_29));
        template.process(msg.getVars(), writer);
        message.setContent(writer.toString(), "text/html;charset=UTF-8");
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        SysPropertiesService propertiesService = SpringUtils.getBean(SysPropertiesService.class);
        Map<String, Object> map = propertiesService.getByGroup("mail");
        properties.setProperty("mail.transport.protocol", String.valueOf(map.get("mail.protocol")));
        properties.setProperty("mail.host", String.valueOf(map.get("mail.host")));
        properties.setProperty("mail.username", String.valueOf(map.get("mail.username")));
        properties.setProperty("mail.password", String.valueOf(map.get("mail.password")));
        properties.setProperty("mail.port", String.valueOf(map.get("mail.port")));
        properties.setProperty("mail.smtp.auth", String.valueOf(map.get("mail.auth")));
        return properties;
    }

    private Session getSession() {
        Properties properties = getProperties();
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = properties.getProperty("mail.username");
                String password = properties.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        session.setDebug(true);
        return session;
    }
}
