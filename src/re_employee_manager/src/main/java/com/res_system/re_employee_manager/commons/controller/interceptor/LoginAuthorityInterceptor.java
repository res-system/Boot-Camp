package com.res_system.re_employee_manager.commons.controller.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.exceptions.AuthException;
import com.res_system.re_employee_manager.commons.model.AuthModel;

/**
 * <pre>
 * ログイン認証を行う インターセプター.
 * </pre>
 * @author res.
 */
@LoginAuthority
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoginAuthorityInterceptor {

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;
    @Context
    private UriInfo uriInfo;


    /**
     * <pre>
     * インターセプトするメソッド.
     * ログイン認証を行う.
     * </pre>
     * @param ic InvocationContext.
     * @return 戻り値.
     * @throws Exception.
     */
    @AroundInvoke
    public Object log(InvocationContext ic) throws Exception{

        // 初期化.
        request.setAttribute("login_group_name",     null);
        request.setAttribute("login_account_name",   null);
        request.setAttribute("login_permission_kbn", null);
        request.setAttribute("is_root_manager",      null);

        // メソッド情報取得
        Method method = ic.getMethod();

        String permissionKbn = "";
        if (method.getAnnotation(NonAuth.class) == null) {

            // ログイン認証チェック.
            int ret = authModel.isLoginAuthOK();
            if (ret < 0) {
                throw new AuthException("ログイン認証がありません。", AuthException.ERROR_LOGIN);
            } else {
                if (ret == 0) {
                    if (!authModel.reloadLoginInfo()) {
                        throw new AuthException("ログイン認証がありません。", AuthException.ERROR_LOGIN);
                    } else {
                        throw new AuthException("セッションが切れました。", AuthException.ERROR_SESSION);
                    }
                }
            }

            // コントローラー.
            String controller = ic.getTarget().getClass().getSuperclass().getAnnotation(Path.class).value();
            controller = ("/".equals(controller.substring(0, 1)))? controller.substring(1): controller;

            // 使用権限区分.
            permissionKbn = authModel.getPermissionKbn(controller);
            if (ReUtil.isEmpty(permissionKbn)) {
                throw new AuthException("『" + controller + "』の使用権限がありません。", AuthException.ERROR_PERMISSION);
            }

        } else {
            if (authModel.getAccountId() <= 0) {
                throw new AuthException("セッションが切れました。", AuthException.ERROR_SESSION);
            }
        }

        // ログイン情報をViewに設定.
        request.setAttribute("login_group_name",     authModel.getSelectedGroupName());
        request.setAttribute("login_account_name",   authModel.getAccountName());
        request.setAttribute("login_permission_kbn", permissionKbn);
        request.setAttribute("is_root_manager",      (authModel.IsRootManager()) ? "1": "0");

        // メソッド実行
        Object result = ic.proceed();
        return result;

    }

}
