package com.res_system.re_emp_manager.model.mnt_emp_info_hd;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * 社員情報ヘッダーマスタメンテナンス処理 フォームクラス.
 * @author res.
 */
public class MntEmpInfoHdForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 入力データ. */
    @DataParam
    private MntEmpInfoHdData data;
    /** 取得リスト. */
    @ListParam(MntEmpInfoHdData.class)
    private List<MntEmpInfoHdData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 処理モード. */
    @Param
    private String mode;

    /** 最大表示順. */
    @Param
    private String maxSeq;


    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 入力データ を取得します. */
    public MntEmpInfoHdData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(MntEmpInfoHdData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<MntEmpInfoHdData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<MntEmpInfoHdData> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }

    /** 処理モード を取得します. */
    public String getMode() { return mode; }
    /** 処理モード を設定します. */
    public void setMode(String mode) { this.mode = mode; }

    /** 最大表示順 を取得します. */
    public String getMaxSeq() { return maxSeq; }
    /** 最大表示順 を設定します. */
    public void setMaxSeq(String maxSeq) { this.maxSeq = maxSeq; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public MntEmpInfoHdForm() {
        super();
        searchCond = new SearchCondition();
        data = new MntEmpInfoHdData();
        list = new ArrayList<>();
    }

}
