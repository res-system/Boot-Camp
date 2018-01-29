package com.res_system.re_employee_manager.model.employee_info;

import java.util.List;

import com.res_system.commons.mvc.model.IListItem;
import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.commons.model.entities.MenuData;

/**
 * <pre>
 * 社員個人情報管理画面 情報クラス.
 * </pre>
 * @author res.
 */
public class EmployeeInfoForm {

    //---------------------------------------------- properies [private].
    /** 社員情報情報. */
    @DataParam
    private EmployeeInfo data;

    /** 状態リスト. */
    private List<IListItem> statusList;
    /** 性別リスト. */
    private List<IListItem> sexList;
    /** メニューデータリスト. */
    private List<MenuData> menuDataList;

    //-- setter / getter. --//
    /** 社員情報情報 を取得します. */
    public EmployeeInfo getData() { return data; }
    /** 社員情報情報 を設定します. */
    public void setData(EmployeeInfo data) { this.data = data; }

    /** 状態リスト を取得します. */
    public List<IListItem> getStatusList() { return statusList; }
    /** 状態リスト を設定します. */
    public void setStatusList(List<IListItem> statusList) { this.statusList = statusList; }
    /** 性別リスト を取得します. */
    public List<IListItem> getSexList() { return sexList; }
    /** 性別リスト を設定します. */
    public void setSexList(List<IListItem> sexList) { this.sexList = sexList; }
    /** メニューデータリスト を取得します. */
    public List<MenuData> getMenuDataList() { return menuDataList; }
    /** メニューデータリスト を設定します. */
    public void setMenuDataList(List<MenuData> menuDataList) { this.menuDataList = menuDataList; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmployeeInfoForm() {
        super();
        data = new EmployeeInfo();
    }

}
