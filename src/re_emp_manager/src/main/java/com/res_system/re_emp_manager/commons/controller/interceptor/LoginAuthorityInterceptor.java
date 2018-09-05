package com.res_system.re_emp_manager.commons.controller.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.exceptions.AuthException;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;

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

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;


    /**
     * <pre>
     * インターセプトするメソッド.
     * </pre>
     * @param ic InvocationContext.
     * @return 戻り値.
     * @throws Exception.
     */
    @AroundInvoke
    public Object proceed(InvocationContext ic) throws Exception{

        // 初期化.
        request.setAttribute("login_user_id",           null);
        request.setAttribute("login_user_name",         null);
        request.setAttribute("login_authority_id",      null);
        request.setAttribute("login_authority_name",    null);
        request.setAttribute("login_group_id",          null);
        request.setAttribute("login_group_name",        null);
        request.setAttribute("login_root_group_id",     null);
        request.setAttribute("login_root_group_name",   null);
        request.setAttribute("login_user_kbn",          null);
        request.setAttribute("login_user_kbn_name",     null);
        request.setAttribute("login_account_user_name", null);

        request.setAttribute("side_menu_list",          null);
        request.setAttribute("acc_menu_list",           null);

        // コントローラー.
        String controller = InterceptorUtil.getController(ic);
        // メソッド情報取得
        Method method = ic.getMethod();

        if (method.getAnnotation(NonAuth.class) == null) {

            // -- ログイン認証チェック --.
            if (ReUtil.isEmpty(auth.getLogin_user_id())) {
                //-- セッションなし. --//
                if (AuthModel.AUTRET_SESSION == auth.isLogin()) {
                    if (auth.reLoadLoginInfo()) {
                        // セッション切れ.
                        throw new AuthException(AuthException.ERROR_SESSION, "セッションが切れました。");
                    }
                }

                // ログイン認証NG.
                auth.clearLogin();
                throw new AuthException(AuthException.ERROR_LOGIN, "ログイン認証がありません。");

            } else {
                //-- セッションあり. --//
                if (AuthModel.AUTRET_OK != auth.isLogin(false)) {
                    // ログイン認証NG.
                    auth.clearLogin();
                    throw new AuthException(AuthException.ERROR_LOGIN, "ログイン認証がありません。");
                }

            }

            // -- 使用権限チェック --.
            if (!auth.isPermission(controller)) {
                auth.clearLogin();
                throw new AuthException(AuthException.ERROR_PERMISSION, "『" + controller + "』の使用権限がありません。");
            }

        }

        // ログイン情報をViewに設定.
        request.setAttribute("login_user_id",           auth.getLogin_user_id());
        request.setAttribute("login_user_name",         auth.getLogin_user_name());
        request.setAttribute("login_authority_id",      auth.getLogin_authority_id());
        request.setAttribute("login_authority_name",    auth.getLogin_authority_name());
        request.setAttribute("login_group_id",          auth.getLogin_group_id());
        request.setAttribute("login_group_name",        auth.getLogin_group_name());
        request.setAttribute("login_root_group_id",     auth.getLogin_root_group_id());
        request.setAttribute("login_root_group_name",   auth.getLogin_root_group_name());
        request.setAttribute("login_user_kbn",          auth.getLogin_user_kbn());
        request.setAttribute("login_user_kbn_name",     auth.getLogin_user_kbn_name());
        request.setAttribute("login_account_user_name", auth.getLogin_account_user_name());

        request.setAttribute("side_menu_list",      auth.getMenuList(AuthModel.MENU_SIDE));
        request.setAttribute("acc_menu_list",       auth.getMenuList(AuthModel.MENU_ACC));

        // メソッド実行
        Object result = ic.proceed();
        return result;
    }

}
