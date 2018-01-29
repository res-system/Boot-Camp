package com.res_system.re_employee_manager.commons.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

import com.res_system.re_employee_manager.commons.model.data.EmpSearchCondition;

/**
 * <pre>
 * 社員選択処理 モデルクラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@SessionScoped
public class SelectedEmpModel implements Serializable {

    //---------------------------------------------- properies [private].
    /** 検索条件データ. */
    private EmpSearchCondition data;
    /** 戻り先. */
    private String returnUrl;

    //-- setter / getter. --//
    /** 検索条件データ を取得します. */
    public EmpSearchCondition getData() { return data; }
    /** 検索条件データ を設定します. */
    public void setData(EmpSearchCondition data) { this.data = data; }
    /** 選択社員ID を取得します. */
    public String getEmployee_id() { return (getData() != null) ? getData().getSelected_employee_id() : null; }
    /** 戻り先 を取得します. */
    public String getReturnUrl() { return returnUrl; }
    /** 戻り先 を設定します. */
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }



    //---------------------------------------------- [public] 会話の開始/終了.
    /** 会話の開始 */
    public void begin() {
        clear();
    }
    /** 会話の終了 */
    public void end() {
        clear();
    }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        clear();
    }



    //---------------------------------------------- [public].
    /** クリアします. */
    public void clear() {
        data = null;
        returnUrl = null;
    }

    /** 社員を選択したか判断します. */
    public boolean isSelectedEmployee() {
        return (getData() != null);
    }

}
