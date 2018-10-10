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
 * 使用許可チェック アノテーション インターセプター.
 * </pre>
 * @author res.
 */
@Permission
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class PermissionInterceptor {

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

        // コントローラー.
        String controller = InterceptorUtil.getController(ic);
        // メソッド情報取得
        Method method = ic.getMethod();

        if (method.getAnnotation(NonAuth.class) == null) {
            // -- 使用許可チェック --.
            if (!ReUtil.isEmpty(auth.getLogin_user_id())) {
            //-- セッションあり. --//
                if (!auth.isPermission(controller)) {
                    // 使用権限NG.
                    auth.clearLogin();
                    throw new AuthException(AuthException.ERROR_PERMISSION, "『" + controller + "』の使用権限がありません。");
                }
                if (!auth.checkGroupId(auth.getLogin_group_id())) {
                    // 選択可能グループNG.
                    auth.clearLogin();
                    throw new AuthException(AuthException.ERROR_GROUP, "『" + auth.getLogin_group_name() + "』は選択出来ません。");
                }
            }
        }

        // メソッド実行
        Object result = ic.proceed();
        return result;
    }

}
