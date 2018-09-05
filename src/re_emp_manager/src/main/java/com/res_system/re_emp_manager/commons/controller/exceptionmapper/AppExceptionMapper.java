package com.res_system.re_emp_manager.commons.controller.exceptionmapper;

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
import com.res_system.re_emp_manager.commons.exceptions.AuthException;

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

    @Context
    private UriInfo uriInfo;
    @Context
    private HttpServletRequest request;

    /**
     * 例外発生時の処理.
     */
    @Override
    public Response toResponse(Exception exception) {
        log.debug("例外が発生しました.", exception);
        if (ReUtil.isEmpty(request.getParameter("is_ajax"))) {

            if (exception instanceof AuthException) {
                if (((AuthException)exception).getErrorKind() == AuthException.ERROR_SESSION) {

                    // セッション切れエラーの場合、メニュー画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/main_menu/show_message/session_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.debug("エラー画面の遷移に失敗しました.", e);
                    }

                } else if (((AuthException)exception).getErrorKind() == AuthException.ERROR_LOGIN) {

                    // ログイン認証エラーの場合、ログイン画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/show_message/login_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.debug("エラー画面の遷移に失敗しました.", e);
                    }

                } else if (((AuthException)exception).getErrorKind() == AuthException.ERROR_PERMISSION
                        || ((AuthException)exception).getErrorKind() == AuthException.ERROR_UPDATE
                        || ((AuthException)exception).getErrorKind() == AuthException.ERROR_GROUP) {

                    // 権限エラーの場合、TOP画面に遷移する.
                    try {
                        URI location = uriInfo.getBaseUriBuilder().path("/show_message/auth_error").build();
                        return Response.seeOther(location).build();
                    } catch (Exception e) {
                        log.debug("エラー画面の遷移に失敗しました.", e);
                    }

                }

            } else if (exception instanceof NonexistentConversationException
                    || exception instanceof BusyConversationException) {
                // 会話スコープ復旧エラーの場合、TOP画面に遷移する.
                try {
                    URI location = uriInfo.getBaseUriBuilder().path("/main_menu/show_message/session_error").build();
                    return Response.seeOther(location).build();
                } catch (Exception e) {
                    log.debug("エラー画面の遷移に失敗しました.", e);
                }
            }

            // その他エラーの場合、TOP画面に遷移する.
            try {
                URI location = uriInfo.getBaseUriBuilder().path("/show_message/system_error").build();
                return Response.seeOther(location).build();
            } catch (Exception e) {
                log.debug("エラー画面の遷移に失敗しました.", e);
            }

        }

        // Ajaxでエラーの場合、サーバーエラーを返す.
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

}
