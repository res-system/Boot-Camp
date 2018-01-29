package com.res_system.re_employee_manager.model.employee_search;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_employee_manager.commons.model.data.EmpSearchCondition;
import com.res_system.re_employee_manager.commons.model.entities.EmployeeInfo;
import com.res_system.re_employee_manager.commons.model.entities.GroupItem;

/**
 * <pre>
 * 社員情報検索[ 一覧 ]画面 情報クラス.
 * </pre>
 * @author res.
 */
public class EmployeeSearchForm {

    //---------------------------------------------- properies [private].
    /** 検索条件データ. */
    @DataParam
    private EmpSearchCondition data;
    /** 取得情報リスト. */
    @ListParam(EmployeeInfo.class)
    private List<EmployeeInfo> list;
    @Param
    /** 取得情報リストサイズ. */
    private String list_size;

    /** グループリスト. */
    private List<GroupItem> groupItemlist;

    //-- setter / getter. --//
    /** 検索条件データ を取得します. */
    public EmpSearchCondition getData() { return data; }
    /** 検索条件データ を設定します. */
    public void setData(EmpSearchCondition data) { this.data = data; }
    /** 取得情報リスト を取得します. */
    public List<EmployeeInfo> getList() { return list; }
    /** 取得情報リスト を設定します. */
    public void setList(List<EmployeeInfo> list) { this.list = list; }
    /** 取得情報リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得情報リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }

    /** グループリスト を取得します. */
    public List<GroupItem> getGroupItemlist() { return groupItemlist; }
    /** グループリスト を設定します. */
    public void setGroupItemlist(List<GroupItem> groupItemlist) { this.groupItemlist = groupItemlist; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmployeeSearchForm() {
        super();
        data = new EmpSearchCondition();
        list = new ArrayList<>();
    }

}
