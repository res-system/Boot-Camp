package com.res_system.re_emp_manager.model.select_group;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * グループ選択処理 フォームクラス.
 * @author res.
 */
public class SelectGroupForm {

    //---------------------------------------------- properies [private].
    /** 入力データ. */
    @DataParam
    private SelectGroupData data;
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 取得リスト. */
    @ListParam(SelectGroupData.class)
    private List<SelectGroupData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    //-- setter / getter. --//
    /** 入力データ を取得します. */
    public SelectGroupData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(SelectGroupData data) { this.data = data; }
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 取得リスト を取得します. */
    public List<SelectGroupData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<SelectGroupData> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }


    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public SelectGroupForm() {
        super();
        data = new SelectGroupData();
        searchCond = new SearchCondition();
        list = new ArrayList<>();
    }

}
