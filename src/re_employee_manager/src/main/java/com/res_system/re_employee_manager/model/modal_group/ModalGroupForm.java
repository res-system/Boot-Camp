package com.res_system.re_employee_manager.model.modal_group;

import java.util.List;

import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * グループ選択ダイアログ 情報クラス.
 * </pre>
 * @author res.
 */
public class ModalGroupForm {

    //---------------------------------------------- properies [private].
    /** 選択グループID. */
    @Param
    private String modal_group_selected_group_id;
    /** 選択グループ名称. */
    @Param
    private String modal_group_selected_group_name;
    /** ページ番号. */
    @Param
    private String modal_group_page;
    /** 総ページ数. */
    @Param
    private String modal_group_total_page;
    /** 総件数. */
    @Param
    private String modal_group_total_size;
    /** 検索キーワード. */
    @Param
    private String modal_group_keyword;

    /** 取得情報リスト. */
    private List<MdGrpCMGroup> list;

    //-- setter / getter. --//
    /** 選択グループID を取得します. */
    public String getModal_group_selected_group_id() { return modal_group_selected_group_id; }
    /** 選択グループID を設定します. */
    public void setModal_group_selected_group_id(String modal_group_selected_group_id) { this.modal_group_selected_group_id = modal_group_selected_group_id; }
    /** 選択グループ名称 を取得します. */
    public String getModal_group_selected_group_name() { return modal_group_selected_group_name; }
    /** 選択グループ名称 を設定します. */
    public void setModal_group_selected_group_name(String modal_group_selected_group_name) { this.modal_group_selected_group_name = modal_group_selected_group_name; }
    /** ページ番号 を取得します. */
    public String getModal_group_page() { return modal_group_page; }
    /** ページ番号 を設定します. */
    public void setModal_group_page(String modal_group_page) { this.modal_group_page = modal_group_page; }
    /** 総ページ数 を取得します. */
    public String getModal_group_total_page() { return modal_group_total_page; }
    /** 総ページ数 を設定します. */
    public void setModal_group_total_page(String modal_group_total_page) { this.modal_group_total_page = modal_group_total_page; }
    /** 総件数 を取得します. */
    public String getModal_group_total_size() { return modal_group_total_size; }
    /** 総件数 を設定します. */
    public void setModal_group_total_size(String modal_group_total_size) { this.modal_group_total_size = modal_group_total_size; }
    /** 検索キーワード を取得します. */
    public String getModal_group_keyword() { return modal_group_keyword; }
    /** 検索キーワード を設定します. */
    public void setModal_group_keyword(String modal_group_keyword) { this.modal_group_keyword = modal_group_keyword; }

    /** 取得情報リスト を取得します. */
    public List<MdGrpCMGroup> getList() { return list; }
    /** 取得情報リスト を設定します. */
    public void setList(List<MdGrpCMGroup> list) { this.list = list; }

}
