package com.res_system.re_employee_manager.commons.controller.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.res_system.re_employee_manager.commons.exceptions.AuthException;
import com.res_system.re_employee_manager.commons.model.AuthModel;

/**
 * <pre>
 * 更新権限認証を行う インターセプター.
 * </pre>
 * @author res.
 */
@UpdateAuthority
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class UpdateAuthorityInterceptor {

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;


    /**
     * <pre>
     * インターセプトするメソッド.
     * 更新権限認証を行う.
     * </pre>
     * @param ic InvocationContext.
     * @return 戻り値.
     * @throws Exception.
     */
    @AroundInvoke
    public Object log(InvocationContext ic) throws Exception{

        // メソッド情報取得
        Method method = ic.getMethod();

        // 認証チェック.
        if (method.getAnnotation(NonAuth.class) == null
                && authModel.hasUpdatePermission()) {
            // メソッド実行
            Object result = ic.proceed();
            return result;
        } else {
            throw new AuthException("更新権限がありません。", AuthException.ERROR_UPDATE);
        }

    }

}
