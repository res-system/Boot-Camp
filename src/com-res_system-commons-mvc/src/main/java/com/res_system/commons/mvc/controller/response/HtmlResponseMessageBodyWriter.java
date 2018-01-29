/**
 *   Copyright (c) 2018 株式会社リスタート.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.res_system.commons.mvc.controller.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.res_system.commons.mvc.view.ThAppTemplateEngine;

/**
 * <pre>
 * Html Response クラスが指定された場合のMessageBodyWriterクラス.
 * </pre>
 * @author res.
 */
@Provider
public class HtmlResponseMessageBodyWriter implements MessageBodyWriter<HtmlResponse> {

    @Context
    private HttpServletRequest httpRequest;
    @Context
    private HttpServletResponse httpResponse;
    @Context
    private ServletContext servletContext;
    @Context
    private UriInfo uriInfo;

    @Inject
    private ThAppTemplateEngine thAppTemplateEngine;

    @Override
    public void writeTo(
            HtmlResponse response,
            Class<?> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream
            ) throws IOException, WebApplicationException {

        if (response.getTemplate() != null && response.getTemplate().length() > 0) {
            // HTMLテキスト出力.
            Writer writer = new OutputStreamWriter(entityStream);
            thAppTemplateEngine.flushHtmlText(response, writer, httpRequest, httpResponse, servletContext);

        } else if (response.getRedirectUrl() != null && response.getRedirectUrl().length() > 0) {
            // Redirect.
            String redirectUrl = response.getRedirectUrl();
            if (redirectUrl.startsWith("/")) {
                URI location = uriInfo.getBaseUriBuilder().path(redirectUrl.substring(1)).build();
                redirectUrl = location.toString();
            }
            httpResponse.sendRedirect(redirectUrl);

        } else {
            throw new RuntimeException("Invalid processing.");
        }

    }

    @Override
    public long getSize(HtmlResponse arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return HtmlResponse.class.isAssignableFrom(type);
    }

}