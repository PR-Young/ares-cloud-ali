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

package com.ares.core.model.base;


import com.ares.core.model.system.SysTemplate;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/06/19
 **/
public class DefaultMessage {
    private String sender;
    private List receiver;
    private Map vars;
    private SysTemplate template;

    public static MessageBuilder newInstance() {
        return new MessageBuilder();
    }

    protected DefaultMessage(MessageBuilder builder) {
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.vars = builder.vars;
        this.template = builder.template;
    }

    public static class MessageBuilder {
        private String sender;
        private List receiver;
        private Map vars;
        private SysTemplate template;

        public MessageBuilder() {
        }

        public MessageBuilder sender(String value) {
            this.sender = value;
            return this;
        }

        public MessageBuilder receivers(List value) {
            this.receiver = value;
            return this;
        }

        public MessageBuilder vars(Map value) {
            this.vars = value;
            return this;
        }

        public MessageBuilder template(SysTemplate value) {
            this.template = value;
            return this;
        }

        public DefaultMessage build() {
            return new DefaultMessage(this);
        }
    }

    public String getSender() {
        return sender;
    }

    public List getReceiver() {
        return receiver;
    }

    public Map getVars() {
        return vars;
    }

    public SysTemplate getTemplate() {
        return template;
    }
}
