package com.res_system.re_emp_manager.commons.controller.interceptor;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.res_system.re_emp_manager.commons.model.conv.ConvModel;

/**
 * <pre>
 * 会話スコープコントローラークラス インターセプター.
 * </pre>
 * @author res.
 */
@ConversationCtrl
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ConversationCtrlInterceptor {

    /** 会話スコープ制御処理 モデルクラス. */
    @Inject 
    private ConvModel conv;


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

        return ic.proceed();
    }

}