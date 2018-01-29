package com.res_system.re_employee_manager.commons.controller.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * コントローラークラス インターセプター.
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
     * ログを出力する.
     * </pre>
     * @param ic InvocationContext.
     * @return 戻り値.
     * @throws Exception.
     */
    @AroundInvoke
    public Object log(InvocationContext ic) throws Exception{

        // 初期化.
        request.setAttribute("request_controller", null);
        request.setAttribute("request_action", null);

        // ターゲットクラス.
        Object target = ic.getTarget();
        // メソッド情報取得
        Method method = ic.getMethod();

        // ターゲット情報.
        String targetInfo = "/" + target.getClass().getSuperclass().getSimpleName() + "/" + method.getName();

        // メソッド開始出力
        log.debug("---------------- start:" + targetInfo);
        // メソッドパラメータ出力
        for(Object parameter : ic.getParameters()) {
            log.debug("  parameter:" + parameter);
        }

        // コントローラー.
        String controller = ic.getTarget().getClass().getSuperclass().getAnnotation(Path.class).value();
        controller = ("/".equals(controller.substring(0, 1)))? controller.substring(1): controller;

        // リクエスト情報.
        request.setAttribute("request_controller", controller);
        request.setAttribute("request_action", method.getName());

        // メソッド実行
        Object result = ic.proceed();

        // メソッド戻り値出力
        log.debug("  return:" + result);
        // メソッド終了出力
        log.debug("----------------   end:" + targetInfo);

        return result;
    }

}