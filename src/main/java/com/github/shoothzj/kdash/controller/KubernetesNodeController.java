/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.kdash.controller;

import com.github.shoothzj.kdash.module.GetNodeResp;
import com.github.shoothzj.kdash.service.KubernetesNodeService;
import io.kubernetes.client.openapi.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/kubernetes")
public class KubernetesNodeController {

    private final KubernetesNodeService kubernetesNodeService;

    public KubernetesNodeController(@Autowired KubernetesNodeService kubernetesNodeService) {
        this.kubernetesNodeService = kubernetesNodeService;
    }

    @GetMapping("/nodes")
    public ResponseEntity<List<GetNodeResp>> getNodes() throws Exception {
        return new ResponseEntity<>(kubernetesNodeService.getNodes(), HttpStatus.OK);
    }

    @PostMapping("/nodes/{nodeName}/label")
    public ResponseEntity<Void> label(@PathVariable String nodeName,
                                      @RequestBody Map<String, String> labels) throws ApiException {
        kubernetesNodeService.label(nodeName, labels);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
