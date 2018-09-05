package com.res_system.re_emp_manager.model.emp_search;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * 社員情報検索処理 フォームクラス.
 * @author res.
 */
public class EmpSearchForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 入力データ. */
    @DataParam
    private EmpSearchData data;
    /** 取得リスト. */
    @ListParam(EmpSearchData.class)
    private List<EmpSearchData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 次画面. */
    @Param
    private String next;


    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 入力データ を取得します. */
    public EmpSearchData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(EmpSearchData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<EmpSearchData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<EmpSearchData> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }

    /** 次画面 を取得します. */
    public String getNext() { return next; }
    /** 次画面 を設定します. */
    public void setNext(String next) { this.next = next; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmpSearchForm() {
        super();
        searchCond = new SearchCondition();
        data = new EmpSearchData();
        list = new ArrayList<>();
    }

}
