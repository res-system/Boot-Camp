package com.res_system.re_emp_manager.commons.controller.interceptor;

import javax.interceptor.InvocationContext;
import javax.ws.rs.Path;

/**
 * インターセプター共通処理.
 * @author res
 *
 */
public class InterceptorUtil {

    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    private InterceptorUtil() {}

    //---------------------------------------------- static [public] 処理.
    /**
     * コントローラー名を取得します.
     * @param ic InvocationContext.
     * @return コントローラー名.
     */
    public static String getController(InvocationContext ic) {
        String controller = "";
        if (ic != null) {
            controller = ic.getTarget().getClass().getSuperclass().getAnnotation(Path.class).value();
            if (controller != null && controller.length() > 0) {
                controller = ("/".equals(controller.substring(0, 1)))? controller.substring(1): controller;
                controller = controller.replaceAll("/\\{.+?\\}", "");
            }
        }
        return controller;
    }

    /**
     * アクション名を取得します.
     * @param ic InvocationContext.
     * @return アクション名.
     */
    public static String getAction(InvocationContext ic) {
        String action = "";
        if (ic != null) {
            Path actionPath = ic.getMethod().getAnnotation(Path.class);
            if (actionPath != null) {
                action = actionPath.value();
                action = ("/".equals(action.substring(0, 1)))? action.substring(1): action;
            }
        }
        return action;
    }

}
