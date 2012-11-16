/*
 * Copyright 2012 Herald Studio, Southeast University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.seu.herald.mvc.router;

import cn.edu.seu.herald.mvc.Controller;
import cn.edu.seu.herald.mvc.Router;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rAy
 */
public class DefaultRouter implements Router {

    private Map<String, Controller> controllerMap;

    public DefaultRouter() {
        controllerMap = new HashMap<String, Controller>();
    }

    public DefaultRouter(Map<String, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public void setControllerMap(Map<String, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public void addController(String path, Controller controller) {
        controllerMap.put(path, controller);
    }

    public void addAllControllers(Map<String, Controller> controllers) {
        controllerMap.putAll(controllers);
    }

    public void removeController(String path) {
        controllerMap.remove(path);
    }

    public void removeAllControllers() {
        controllerMap.clear();
    }

    public Set<String> getAllPaths() {
        return controllerMap.keySet();
    }

    public Controller getControllerByPath(String path) {
        return controllerMap.get(path);
    }
}
