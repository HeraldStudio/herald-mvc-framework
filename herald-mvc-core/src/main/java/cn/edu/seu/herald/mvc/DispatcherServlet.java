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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy
 */
public class DispatcherServlet extends HttpServlet {

    protected final String ROUTER_CLASS_NAME_INIT_PARAM =
            "cn.edu.seu.herald.mvc.router";
    private static final Logger logger = Logger.getLogger(
            DispatcherServlet.class.getName());
    private Router router;

    private static void logMessages(Level level, String ...msgs) {
        StringBuilder msgBuilder = new StringBuilder();
        for (String msg: msgs) {
            msgBuilder.append(msg);
        }
        logger.log(level, msgBuilder.toString());
    }

    private static void logException(Exception ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        logMessages(Level.INFO, "Initializing the dispatcher servlet");
        logMessages(Level.INFO, "Reading the router from the init parameter");
        String routerClzName = config.getInitParameter(
                ROUTER_CLASS_NAME_INIT_PARAM);
        logMessages(Level.INFO, "Using the router: ", routerClzName);
        try {
            Class<?> routerClz = Class.forName(routerClzName);
            Constructor constructor = routerClz.getConstructor();
            router = (Router) constructor.newInstance();
        } catch (NoSuchMethodException ex) {
            logMessages(Level.SEVERE, "Rounter class: ", routerClzName,
                    " requires a constructor with parameters");
            logException(ex);
        } catch (Exception ex) {
            logException(ex);
        }
    }

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String requestPath = request.getRequestURI();
        Controller controller = router.getControllerByPath(requestPath);
        ModelAndView modelAndView;
        try {
            modelAndView = controller
                    .handleRequest(request, response);
        } catch (Exception ex) {
            logMessages(Level.SEVERE,
                    "exception occurred during controller.handleRequest()");
            logException(ex);
            response.sendError(500, "Internal server error");
            return;
        }

        View view = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        try {
            view.render(model, request, response);
        } catch (Exception ex) {
            logMessages(Level.SEVERE,
                    "exception occurred during view.render()");
            logException(ex);
            response.sendError(500, "Internal server error");
        }
    }
}
