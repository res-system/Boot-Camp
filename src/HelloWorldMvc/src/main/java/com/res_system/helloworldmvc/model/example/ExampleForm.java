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

    //-- setter / getter. --//
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     * @param message メッセージ.
     */
    public ExampleForm() {
        super();
    }

}
