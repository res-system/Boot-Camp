package com.res_system.re_employee_manager.commons.exceptions;

/**
 * <pre>
 * アプリケーション業務エラー例外クラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

}
