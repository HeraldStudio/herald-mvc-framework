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
package cn.edu.seu.herald.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rAy
 */
public class ModelAndView {

    private View view;
    private Map<String, Object> model;

    public ModelAndView() {
        model = new HashMap<String, Object>();
    }

    public ModelAndView(View view) {
        this.view = view;
        model = new HashMap<String, Object>();
    }

    public ModelAndView(View view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public void addObject(String name, Object value) {
        model.put(name, value);
    }

    public void addAllObjects(Map<String, Object> model) {
        model.putAll(model);
    }

    public void removeObject(String name) {
        model.remove(name);
    }

    public void removeAllObjects() {
        model.clear();
    }
}
