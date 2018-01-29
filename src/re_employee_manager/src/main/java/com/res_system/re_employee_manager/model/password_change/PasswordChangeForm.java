package com.res_system.re_employee_manager.model.password_change;

import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * パスワード変更画面 情報クラス.
 * </pre>
 * @author res.
 */
public class PasswordChangeForm {

    //---------------------------------------------- properies [private].
    /** 旧パスワード. */
    @Param
    private String password;
    /** 新パスワード. */
    @Param
    private String new_password;
    /** 確認用パスワード. */
    @Param
    private String confirmation_password;

    //-- setter / getter. --//
    /** 旧パスワード を取得します. */
    public String getPassword() { return password; }
    /** 旧パスワード を設定します. */
    public void setPassword(String password) { this.password = password; }
    /** 新パスワード を取得します. */
    public String getNew_password() { return new_password; }
    /** 新パスワード を設定します. */
    public void setNew_password(String new_password) { this.new_password = new_password; }
    /** 確認用パスワード を取得します. */
    public String getConfirmation_password() { return confirmation_password; }
    /** 確認用パスワード を設定します. */
    public void setConfirmation_password(String confirmation_password) { this.confirmation_password = confirmation_password; }

}
