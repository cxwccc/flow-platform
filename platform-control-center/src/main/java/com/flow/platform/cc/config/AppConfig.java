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

package com.flow.platform.cc.config;

import com.flow.platform.cc.domain.CmdStatusItem;
import com.flow.platform.core.config.AbstractAppConfig;
import com.flow.platform.core.config.DatabaseConfig;
import com.flow.platform.core.util.SpringContextUtil;
import com.flow.platform.domain.AgentPath;
import com.flow.platform.domain.Jsonable;
import com.flow.platform.util.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author gy@fir.im
 */
@Configuration
@Import({
    DatabaseConfig.class,
    ZooKeeperConfig.class,
    MQConfig.class,
    TaskConfig.class,
    WebSocketConfig.class,
    AgentConfig.class
})
public class AppConfig extends AbstractAppConfig{

    public final static DateTimeFormatter APP_DATE_FORMAT = Jsonable.DOMAIN_DATE_FORMAT;

    public final static Path CMD_LOG_DIR = Paths.get(System.getenv("HOME"), "uploaded-agent-log");

    private final static int ASYNC_POOL_SIZE = 100;

    private final static Logger LOGGER = new Logger(AppConfig.class);

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(CMD_LOG_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    @Override
    public ThreadPoolTaskExecutor taskExecutor() {
        return super.taskExecutor();
    }

    /**
     * Queue to handle agent report online in sync
     */
    @Bean
    public BlockingQueue<AgentPath> agentReportQueue() {
        return new LinkedBlockingQueue<>(50);
    }

    /**
     * Queue to handle cmd status update
     */
    @Bean
    public BlockingQueue<CmdStatusItem> cmdStatusQueue() {
        return new LinkedBlockingQueue<>(50);
    }

    @Bean
    public SpringContextUtil springContextUtil(){
        return new SpringContextUtil();
    }
}
