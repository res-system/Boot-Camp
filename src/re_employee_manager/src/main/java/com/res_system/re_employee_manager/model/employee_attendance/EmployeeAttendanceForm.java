package com.res_system.re_employee_manager.model.employee_attendance;

import java.util.ArrayList;
import java.util.List;

import com.res_system.re_employee_manager.commons.model.entities.MenuData;

/**
 * <pre>
 * 社員家族情報管理画面 情報クラス.
 * </pre>
 * @author res.
 */
public class EmployeeAttendanceForm {

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
    public EmployeeAttendanceForm() {
        super();
        menuDataList = new ArrayList<>();
    }

}
