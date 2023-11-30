/**
 * Copyright (C) Posten Norge AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.digipost.jaxb.xjc;


import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JFormatter;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Map;


public class JacksonNillable extends Plugin {

    protected static final String OPTION_NAME = "Xjacksonfive";


    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }


    @Override
    public String getUsage() {
        return "-" + OPTION_NAME +
                "    :   Always add @XmlElement(nillable=false) i nillable=false and minoccurs=0 is set in xml to help Jackson\n";
    }


    @Override
    public boolean run(final Outline model, final Options opts, final ErrorHandler errors) throws SAXException {
        for (ClassOutline co : model.getClasses()) {
            Map<String, JFieldVar> fields = co.implClass.fields();
            for (Map.Entry<String, JFieldVar> entry : fields.entrySet()) {
                JFieldVar field = entry.getValue();
                boolean skip = false;
                JAnnotationUse element = null;
                for (JAnnotationUse a : field.annotations()) {
                    String annotation = asString(a);
                    skip = true; // This field is annotated, we should most
                                 // likely skip it
                    if (isAnnotatedWith(annotation, XmlElementRef.class)) {
                        continue;
                    }
                    if (isAnnotatedWith(annotation, XmlElement.class)) {
                        if (!annotation.matches(".*required[ ]*=[ ]*true.*")) {
                            // The annotation was XmlElement, but it did not
                            // have required = true. Let's add the annotation
                            element = a;
                            skip = false;
                        }
                        break;
                    }
                }
                if (!skip) {
                    if (element == null) {
                        element = field.annotate(XmlElement.class);
                    }
                    element.param("nillable", false);
                }
            }

        }
        return true;
    }


    private static String asString(final JAnnotationUse a) {
        StringWriter writer = new StringWriter();
        a.generate(new JFormatter(writer));
        String annotation = writer.toString();
        return annotation;
    }


    @SafeVarargs
    private static final boolean isAnnotatedWith(String annotation, Class<? extends Annotation>... classes) {
        for (Class<? extends Annotation> c : classes) {
            if (annotation.contains(c.getName())) { return true; }
        }
        return false;
    }

}
