package com.res_system.re_employee_manager.commons.exceptions;

/**
 * <pre>
 * 権限エラー例外クラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
public class AuthException extends Exception {

    //---------------------------------------------- const [public].
    /** エラー種別[ログイン認証]. */
    public static final int ERROR_LOGIN = 0;
    /** エラー種別[権限許可]. */
    public static final int ERROR_PERMISSION = 1;
    /** エラー種別[更新権限]. */
    public static final int ERROR_UPDATE = 2;
    /** エラー種別[セッション切れ]. */
    public static final int ERROR_SESSION = 3;



    //---------------------------------------------- properies [private].
    /** エラー種別. */
    int errorKind = 0;

    //-- setter / getter. --//
    /** エラー種別 を取得します. */
    public int getErrorKind() { return errorKind; }
    /** エラー種別 を設定します. */
    public void setErrorKind(int errorKind) { this.errorKind = errorKind; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public AuthException() {
        super();
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(int errorKind) {
        super();
        this.errorKind = errorKind;
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorKind) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorKind = errorKind;
    }

    public AuthException(String message, Throwable cause, int errorKind) {
        super(message, cause);
        this.errorKind = errorKind;
    }

    public AuthException(String message, int errorKind) {
        super(message);
        this.errorKind = errorKind;
    }

    public AuthException(Throwable cause, int errorKind) {
        super(cause);
        this.errorKind = errorKind;
    }

}
