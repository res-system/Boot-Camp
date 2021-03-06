package com.res_system.re_emp_manager.model.mnt_group_acc;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * グループアカウント情報メンテナンス処理 フォームクラス.
 * @author res.
 */
public class MntGroupAccForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 入力データ. */
    @DataParam
    private MntGroupAccData data;
    /** 取得リスト. */
    @ListParam(MntGroupAccData.class)
    private List<MntGroupAccData> list;
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
    public MntGroupAccData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(MntGroupAccData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<MntGroupAccData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<MntGroupAccData> list) { this.list = list; }
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
    public MntGroupAccForm() {
        super();
        searchCond = new SearchCondition();
        data = new MntGroupAccData();
        list = new ArrayList<>();
    }

}
