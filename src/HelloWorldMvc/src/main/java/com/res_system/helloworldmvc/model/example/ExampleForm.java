package com.res_system.helloworldmvc.model.example;

import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * フォームクラス(テスト用)(画面とデータを遣り取りするクラス).
 * </pre>
 * @author res.
 */
public class ExampleForm {

    //---------------------------------------------- properies [private].
    /** メッセージ. */
    @Param
    private String message;
    @Param
    private String result;
    @Param
    private String submit;

    //-- setter / getter. --//
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getSubmit() { return submit; }
    public void setSubmit(String submit) { this.submit = submit; }

}
