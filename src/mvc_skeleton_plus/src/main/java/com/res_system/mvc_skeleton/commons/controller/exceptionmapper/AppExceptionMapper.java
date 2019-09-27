package com.res_system.mvc_skeleton.commons.controller.exceptionmapper;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.contexts.BusyConversationException;
import org.jboss.weld.contexts.NonexistentConversationException;

import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.exceptions.AuthException;

/**
 * <pre>
 * アプリケーション 例外マッピング処理クラス.
 * </pre>
 * @author res.
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

    /** ロガー. */
    private static final Logger log = LogManager.getLogger(AppExceptionMapper.class);

    /** uri情報１オブジェクト. */
    @Context
    private UriInfo uriInfo;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;



    /**
     * 例外発生時の処理.
     */
    @Override
    public Response toResponse(Exception exception) {
        log.error("例外が発生しました.", exception);

        if (ReUtil.isEmpty(request.getParameter("is_ajax"))) {

            if (exception instanceof AuthException) {
                if (((AuthException)exception).getErrorKind() == AuthException.ERROR_LOGIN) {

                    // ログイン認証エラーの場合、エラー画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/show_message/login_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.error("エラー画面の遷移に失敗しました.", e);
                    }

                } else if (((AuthException)exception).getErrorKind() == AuthException.ERROR_PERMISSION
                        || ((AuthException)exception).getErrorKind() == AuthException.ERROR_UPDATE
                        || ((AuthException)exception).getErrorKind() == AuthException.ERROR_GROUP) {

                    // 権限エラーの場合、TOP画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/show_message/auth_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.error("エラー画面の遷移に失敗しました.", e);
                    }

                } else if (((AuthException)exception).getErrorKind() == AuthException.ERROR_SESSION) {

                    // セッション切れエラーの場合、TOP画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/show_message/session_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.error("エラー画面の遷移に失敗しました.", e);
                    }

                }

            } else if (exception instanceof NonexistentConversationException
                    || exception instanceof BusyConversationException) {

                // 会話スコープ復旧エラーの場合、TOP画面に遷移する.
                try {
                    URI location = uriInfo.getBaseUriBuilder().path("/show_message/session_error").build();
                    return Response.seeOther(location).build();
                } catch (Exception e) {
                    log.error("エラー画面の遷移に失敗しました.", e);
                }

            }

            // その他エラーの場合、エラー画面に遷移する.
            try {
                URI location = uriInfo.getBaseUriBuilder().path("/show_message/system_error").build();
                return Response.seeOther(location).build();
            } catch (Exception e) {
                log.error("エラー画面の遷移に失敗しました.", e);
            }

        }

        // Ajaxでエラーの場合、サーバーエラーを返す.
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }



}
