package com.res_system.re_emp_manager.model.mnt_emp_user;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * 社員ユーザー情報メンテナンス処理 フォームクラス.
 * @author res.
 */
public class MntEmpUserForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 入力データ. */
    @DataParam
    private MntEmpUserData data;
    /** 取得リスト. */
    @ListParam(MntEmpUserData.class)
    private List<MntEmpUserData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 処理モード. */
    @Param
    private String mode;


    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 入力データ を取得します. */
    public MntEmpUserData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(MntEmpUserData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<MntEmpUserData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<MntEmpUserData> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }

    /** 処理モード を取得します. */
    public String getMode() { return mode; }
    /** 処理モード を設定します. */
    public void setMode(String mode) { this.mode = mode; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public MntEmpUserForm() {
        super();
        searchCond = new SearchCondition();
        data = new MntEmpUserData();
        list = new ArrayList<>();
    }

}
