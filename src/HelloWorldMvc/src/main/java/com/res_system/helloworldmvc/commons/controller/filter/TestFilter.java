package com.res_system.helloworldmvc.commons.controller.filter;

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
 * フィルター処理クラス(テスト用).
 * </pre>
 * @author res.
 */
@Provider
public class TestFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger log = LogManager.getLogger(TestFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo uriInfo = requestContext.getUriInfo();
        log.debug("【" + uriInfo.getRequestUri().toString() + "】");
        log.debug("  Headers:" + requestContext.getHeaders().toString() + "  ");
        log.debug("  -- start -- TestFilter.filter(ContainerRequestContext)");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.debug("  --   end -- TestFilter.filter(ContainerRequestContext, ContainerResponseContext)");
    }

}
