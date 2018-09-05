package com.res_system.re_emp_manager.model.change_password;

import com.res_system.commons.mvc.model.form.Param;

/**
 * パスワード変更画面 フォームクラス.
 * @author res.
 */
public class ChangePasswordForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @Param
    /** 旧パスワード. */
    private String password;
    @Param
    /** 新パスワード. */
    private String new_password;
    @Param
    /** 確認用パスワード. */
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
