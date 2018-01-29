package com.res_system.re_employee_manager.commons.model.data;

import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * 社員情報検索条件 情報クラス.
 * </pre>
 * @author res.
 */
public class EmpSearchCondition {

    //---------------------------------------------- properies [private].
    /** 検索キーワード. */
    @Param
    private String keyword;
    /** 全状態を含むチェック. */
    @Param
    private String is_all;
    /** ページ番号. */
    @Param
    private String page;
    /** 総ページ数. */
    @Param
    private String total_page;
    /** 総件数. */
    @Param
    private String total_size;
    /** 選択社員ID. */
    @Param
    private String selected_employee_id;

    //-- setter / getter. --//
    /** 検索キーワード を取得します. */
    public String getKeyword() { return keyword; }
    /** 検索キーワード を設定します. */
    public void setKeyword(String keyword) { this.keyword = keyword; }
    /** 全状態を含むチェック を取得します. */
    public String getIs_all() { return is_all; }
    /** 全状態を含むチェック を設定します. */
    public void setIs_all(String is_all) { this.is_all = is_all; }
    /** ページ番号 を取得します. */
    public String getPage() { return page; }
    /** ページ番号 を取得します. */
    public void setPage(String page) { this.page = page; }
    /** 総ページ数 を取得します. */
    public String getTotal_page() { return total_page; }
    /** 総ページ数 を取得します. */
    public void setTotal_page(String total_page) { this.total_page = total_page; }
    /** 総件数 を取得します. */
    public String getTotal_size() { return total_size; }
    /** 総件数 を取得します. */
    public void setTotal_size(String total_size) { this.total_size = total_size; }
    /** 選択社員ID を取得します. */
    public String getSelected_employee_id() { return selected_employee_id; }
    /** 選択社員ID を設定します. */
    public void setSelected_employee_id(String selected_employee_id) { this.selected_employee_id = selected_employee_id; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmpSearchCondition() {
        super();
    }

}
