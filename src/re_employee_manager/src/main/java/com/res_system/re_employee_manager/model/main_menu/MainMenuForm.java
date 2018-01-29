package com.res_system.re_employee_manager.model.main_menu;

import java.util.List;

import com.res_system.re_employee_manager.commons.model.entities.MenuData;

/**
 * <pre>
 * メインメニュー画面 情報クラス.
 * </pre>
 * @author res.
 */
public class MainMenuForm {

    //---------------------------------------------- properies [private].
    /** メニューデータリスト. */
    private List<MenuData> menuDataList;
    /** メッセージ区分. */
    private String messageKbn;
    /** グループの選択有無. */
    private String selectGroup;

    //-- setter / getter. --//
    /** メニューデータリスト を取得します. */
    public List<MenuData> getMenuDataList() { return menuDataList; }
    /** メニューデータリスト を設定します. */
    public void setMenuDataList(List<MenuData> menuDataList) { this.menuDataList = menuDataList; }
    /** メッセージ区分 を取得します. */
    public String getMessageKbn() { return messageKbn; }
    /** メッセージ区分 を設定します. */
    public void setMessageKbn(String messageKbn) { this.messageKbn = messageKbn; }
    /** グループの選択有無 を取得します. */
    public String getSelectGroup() { return selectGroup; }
    /** グループの選択有無 を設定します. */
    public void setSelectGroup(String selectGroup) { this.selectGroup = selectGroup; }

}
