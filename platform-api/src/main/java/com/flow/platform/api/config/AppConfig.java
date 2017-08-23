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

package com.flow.platform.api.config;

import com.flow.platform.api.domain.CmdQueueItem;
import com.flow.platform.core.config.AppConfigBase;
import com.flow.platform.core.config.DatabaseConfig;
import com.flow.platform.core.sysinfo.PropertySystemInfo;
import com.flow.platform.core.sysinfo.SystemInfo;
import com.flow.platform.core.sysinfo.SystemInfo.Status;
import com.flow.platform.util.DateUtil;
import com.flow.platform.util.Logger;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.support.StandardServletEnvironment;

/**
 * @author yang
 */
@Configuration
@Import({DatabaseConfig.class})
public class AppConfig extends AppConfigBase {

    public final static String NAME = "flow-api";

    public final static String VERSION = "alpha-0.1";

    public final static String DEFAULT_YML_FILE = ".flow.yml";

    public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final static Logger LOGGER = new Logger(AppConfig.class);

    private final static int ASYNC_POOL_SIZE = 100;

    private final static String THREAD_NAME_PREFIX = "async-task-";

    @Value("${api.workspace}")
    private String workspace;

    @Bean
    public Path workspace() {
        try {
            Path dir = Files.createDirectories(Paths.get(workspace));
            LOGGER.trace("flow.ci working dir been created : %s", dir);
            return dir;
        } catch (IOException e) {
            throw new RuntimeException("Fail to create flow.ci api working dir", e);
        }
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        return super.taskExecutor(ASYNC_POOL_SIZE, THREAD_NAME_PREFIX);
    }

    @Bean
    public BlockingQueue<CmdQueueItem> cmdBaseBlockingQueue() {
        return new LinkedBlockingQueue<>(50);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    protected String getVersion() {
        return VERSION;
    }
}