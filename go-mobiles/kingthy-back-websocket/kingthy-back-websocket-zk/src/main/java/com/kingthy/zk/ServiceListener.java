/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */

package com.kingthy.zk;

import com.kingthy.dto.ServiceNode;

/**
 * 描述：节点连接侦听
 * @author  likejie
 * @date 2017/10/26.
 */
public interface ServiceListener {

    /**
     * 服务节点添加
     * @param path
     * @param node
     *
     */
    void onServiceAdded(String path, ServiceNode node);
    /**
     * 服务节点更新
     * @param path
     * @param node
     *
     */
    void onServiceUpdated(String path, ServiceNode node);
    /**
     * 服务节点删除
     * @param path
     * @param node
     *
     */
    void onServiceRemoved(String path, ServiceNode node);

}
