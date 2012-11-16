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
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author rAy
 */
public class JaxbXmlView implements View {

    public static final String JAXB_OBJECT_NAME =
            "cn.edu.seu.herald.mvc.view.JaxbObject";
    private Class<?> jaxbClz;
    private Object jaxbObj;

    public JaxbXmlView(Class<?> jaxbClz) {
        this.jaxbClz = jaxbClz;
    }

    public void setJaxbObject(Object jaxbObj) {
        this.jaxbObj = jaxbObj;
    }

    public void render(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/xml");
        Object bindingObj = (jaxbObj == null)
                ? model.get(JAXB_OBJECT_NAME)
                : jaxbObj;

        if (bindingObj == null) {
            throw new RuntimeException(
                    "No xml binding object added to this JaxbXmlView\n");
        }

        PrintWriter out = response.getWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(bindingObj, out);
        } finally {
            out.close();
        }
    }
}
