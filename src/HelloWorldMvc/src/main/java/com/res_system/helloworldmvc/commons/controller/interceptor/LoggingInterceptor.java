package com.res_system.helloworldmvc.commons.controller.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * ログを出力する インターセプター(テスト用).
 * </pre>
 * @author res.
 */
@Logging
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

    private static final Logger log = LogManager.getLogger(LoggingInterceptor.class);

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

        // メソッド実行
        Object result = ic.proceed();

        // メソッド戻り値出力
        log.debug("  return:" + result);
        // メソッド終了出力
        log.debug("----------------   end:" + targetInfo);

        return result;
    }

}