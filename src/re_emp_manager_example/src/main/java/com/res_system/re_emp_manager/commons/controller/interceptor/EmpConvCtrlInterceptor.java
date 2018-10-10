package com.res_system.re_emp_manager.commons.controller.interceptor;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.employee.EmployeeConvModel;

/**
 * <pre>
 * 社員情報用 会話スコープコントローラークラス アノテーションインターセプター.
 * </pre>
 * @author res.
 */
@EmpConvCtrl
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class EmpConvCtrlInterceptor {

    /** 会話スコープ制御処理 モデルクラス. */
    @Inject 
    private EmployeeConvModel conv;

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

        // @ConversationScoped対応.
        conv.setConversationId();

        // 一覧からの選択ユーザーの有無.
        conv.setSelectedUserId();
        // タブメニューリスト.
        request.setAttribute("emp_menu_list", auth.getMenuList(AuthModel.MENU_EMP));
        // 社員データ.
        request.setAttribute("emp_data", conv.findEmployeeData());

        return ic.proceed();
    }

}