package com.res_system.re_employee_manager.model.employee_expenses;

import java.util.ArrayList;
import java.util.List;

import com.res_system.re_employee_manager.commons.model.entities.MenuData;

/**
 * <pre>
 * 社員交通費情報管理 情報クラス.
 * </pre>
 * @author res.
 */
public class EmployeeExpensesForm {

    //---------------------------------------------- properies [private].
    /** メニューデータリスト. */
    private List<MenuData> menuDataList;

    //-- setter / getter. --//
    /** メニューデータリスト を取得します. */
    public List<MenuData> getMenuDataList() { return menuDataList; }
    /** メニューデータリスト を設定します. */
    public void setMenuDataList(List<MenuData> menuDataList) { this.menuDataList = menuDataList; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmployeeExpensesForm() {
        super();
        menuDataList = new ArrayList<>();
    }

}
