package com.res_system.helloworldmvc.commons.controller.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * 例外マッピング処理クラス(テスト用).
 * </pre>
 * @author res.
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LogManager.getLogger(AppExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        log.debug("例外が発生しました.", exception);
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

}
