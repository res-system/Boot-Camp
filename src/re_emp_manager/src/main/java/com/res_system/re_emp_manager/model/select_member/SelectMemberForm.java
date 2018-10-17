package com.res_system.re_emp_manager.model.select_member;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * メンバー選択処理 フォームクラス.
 * @author res.
 */
public class SelectMemberForm {

    //---------------------------------------------- properies [private].
    /** 入力データ. */
    @DataParam
    private SelectMemberData data;
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 取得リスト. */
    @ListParam(SelectMemberData.class)
    private List<SelectMemberData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    //-- setter / getter. --//
    /** 入力データ を取得します. */
    public SelectMemberData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(SelectMemberData data) { this.data = data; }
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 取得リスト を取得します. */
    public List<SelectMemberData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<SelectMemberData> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }


    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public SelectMemberForm() {
        super();
        data = new SelectMemberData();
        searchCond = new SearchCondition();
        list = new ArrayList<>();
    }

}
