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

package com.flow.platform.api.controller;

import com.flow.platform.api.domain.Flow;
import com.flow.platform.api.service.NodeService;
import com.flow.platform.api.validator.NodeNameValidator;
import com.flow.platform.exception.IllegalParameterException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yh@firim
 */

@RestController
@RequestMapping(path = "/flows")
public class FlowController {

    @Autowired
    private NodeService nodeService;

    @GetMapping
    public List<Flow> index() {
        return nodeService.listFlows();
    }

    @PostMapping("/{flowName}")
    public Flow createEmptyFlow(@Validated(NodeNameValidator.class) @PathVariable String flowName) {
        return nodeService.createEmptyFlow(flowName);
    }

    /**
     * Check flow name is exist
     */
    @GetMapping("/exist/{flowName}")
    public Boolean isFlowNameExist(@Validated(NodeNameValidator.class) @PathVariable String flowName) {
        return nodeService.isExistedFlow(flowName);
    }
}
