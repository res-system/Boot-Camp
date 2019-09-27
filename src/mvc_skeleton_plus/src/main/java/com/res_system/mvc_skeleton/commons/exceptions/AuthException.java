package com.res_system.mvc_skeleton.commons.exceptions;

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
    /** エラー種別[グループ]. */
    public static final int ERROR_GROUP = 4;



    //---------------------------------------------- properies [private].
    /** エラー種別. */
    int errorKind = 0;
    /** 戻り先URL. */
    String returnUrl = "";

    //-- setter / getter. --//
    /** エラー種別 を取得します. */
    public int getErrorKind() { return errorKind; }
    /** エラー種別 を設定します. */
    public void setErrorKind(int errorKind) { this.errorKind = errorKind; }
    /** 戻り先URL を取得します. */
    public String getReturnUrl() { return returnUrl; }
    /** 戻り先URL を設定します. */
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     * @param errorKind
     * @param message
     */
    public AuthException(int errorKind, String message) {
        super(message);
        this.errorKind = errorKind;
    }

    /**
     * コンストラクタ.
     * @param errorKind
     * @param message
     * @param cause
     */
    public AuthException(int errorKind, String message, Throwable cause) {
        super(message, cause);
        this.errorKind = errorKind;
    }

    /**
     * コンストラクタ.
     * @param errorKind
     * @param message
     * @param returnUrl
     */
    public AuthException(int errorKind, String message, String returnUrl) {
        super(message);
        this.errorKind = errorKind;
        this.returnUrl = returnUrl;
    }

    /**
     * コンストラクタ.
     * @param errorKind
     * @param message
     * @param returnUrl
     * @param cause
     */
    public AuthException(int errorKind, String message, String returnUrl, Throwable cause) {
        super(message, cause);
        this.errorKind = errorKind;
        this.returnUrl = returnUrl;
    }

    /**
     * コンストラクタ.
     * @param errorKind
     * @param message
     * @param returnUrl
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public AuthException(int errorKind, String message, String returnUrl, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorKind = errorKind;
        this.returnUrl = returnUrl;
    }

}
