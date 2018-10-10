package com.res_system.re_emp_manager.commons.controller.interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.util.ReUtil;

/**
 * <pre>
 * コントローラークラス アノテーション インターセプター.
 * </pre>
 * @author res.
 */
@Controller
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ControllerInterceptor {

    /** ロガー. */
    private static final Logger log = LogManager.getLogger(ControllerInterceptor.class);

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
        request.setAttribute("request_controller",  null);
        request.setAttribute("request_action",      null);

        // コントローラー.
        String controller = InterceptorUtil.getController(ic);
        if (ReUtil.isEmpty(controller)) {
            controller = "top";
        }

        // アクション.
        String action = InterceptorUtil.getAction(ic);
        if (ReUtil.isEmpty(action)) {
            action = ic.getMethod().getName();
        }

        // リクエスト情報.
        request.setAttribute("request_controller", controller);
        request.setAttribute("request_action", action);

        //-- アクションメソッド実行 --//
        String targetInfo = "/" + controller + "/" + action;
        log.debug("---------------- start:" + targetInfo);
        for (Object parameter : ic.getParameters()) {
            log.debug("  parameter:" + parameter);
        }
        Object result = ic.proceed();
        log.debug("  return:" + result);
        log.debug("----------------   end:" + targetInfo);
        return result;
    }

}