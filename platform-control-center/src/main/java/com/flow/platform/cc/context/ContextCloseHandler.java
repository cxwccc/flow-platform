/*
 * Copyright 2017 flow.ci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flow.platform.cc.context;

import com.flow.platform.core.context.AbstractContextCloseHandler;
import com.flow.platform.core.util.SpringContextUtil;
import com.flow.platform.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Listen spring application context event and shutdown running async tasks
 *
 * @author gy@fir.im
 */
@Component
public class ContextCloseHandler extends AbstractContextCloseHandler {

    private final static Logger LOGGER = new Logger(ContextCloseHandler.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Override
    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    @Override
    public com.flow.platform.core.util.SpringContextUtil getSpringContextUtil() {
        return springContextUtil;
    }
}
