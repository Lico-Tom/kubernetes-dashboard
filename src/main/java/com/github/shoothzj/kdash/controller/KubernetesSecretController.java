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

import com.github.shoothzj.kdash.module.CreateSecretReq;
import com.github.shoothzj.kdash.module.YamlReq;
import com.github.shoothzj.kdash.service.KubernetesSecretService;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1SecretList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/kubernetes")
public class KubernetesSecretController {

    private final KubernetesSecretService kubernetesSecretService;

    public KubernetesSecretController(@Autowired KubernetesSecretService kubernetesSecretService) {
        this.kubernetesSecretService = kubernetesSecretService;
    }

    @PutMapping("/namespace/{namespace}/secrets")
    public ResponseEntity<Void> createSecret(@PathVariable String namespace,
                                              @RequestBody CreateSecretReq req) throws ApiException {
        kubernetesSecretService.createSecret(namespace, req);
    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/namespace/{namespace}/secrets/yaml")
    public ResponseEntity<Void> createSecretByYaml(@PathVariable String namespace,
                                             @RequestBody YamlReq req) throws ApiException, IOException {
        kubernetesSecretService.createSecretByYaml(namespace, req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/namespace/{namespace}/secrets/{secret}")
    public ResponseEntity<Void> deleteSecret(@PathVariable String namespace,
                                             @PathVariable String secret) throws ApiException {
        kubernetesSecretService.deleteSecret(namespace, secret);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/namespace/{namespace}/secrets")
    public ResponseEntity<V1SecretList> getSecretList(@PathVariable String namespace) throws ApiException {
        return new ResponseEntity<>(kubernetesSecretService.getSecretList(namespace), HttpStatus.OK);
    }

}
