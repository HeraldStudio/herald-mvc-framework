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
package cn.edu.seu.herald.mvc.view;

import cn.edu.seu.herald.mvc.View;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy
 */
public class JspView implements View {

    private String jspPath;

    private String prefix = "";

    public JspView(String jspPath) {
        this.jspPath = jspPath;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void render(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        // add all model objects to the request attributes
        for (String modelKey: model.keySet()) {
            Object modelObj = model.get(modelKey);
            request.setAttribute(modelKey, modelObj);
        }
        // dispatch to the jsp
        request.getRequestDispatcher(getFullJspPath())
                .forward(request, response);
    }

    private String getFullJspPath() {
        return new StringBuilder().append(prefix).append(jspPath).toString();
    }
}
