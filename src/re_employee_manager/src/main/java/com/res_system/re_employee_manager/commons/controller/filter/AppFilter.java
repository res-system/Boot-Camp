package com.res_system.re_employee_manager.commons.controller.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * アプリケーション フィルター処理クラス.
 * </pre>
 * @author res.
 */
@Provider
public class AppFilter implements ContainerRequestFilter, ContainerResponseFilter {

    /** ロガー. */
    private static final Logger log = LogManager.getLogger(AppFilter.class);
    static {
        log.debug("【application start】");
        // アプリケーション開始時に必要な設定を行う.
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo uriInfo = requestContext.getUriInfo();
        log.debug("【" + uriInfo.getRequestUri().toString() + "】");
        log.debug("  Headers:" + requestContext.getHeaders().toString() + "  ");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    }

}
