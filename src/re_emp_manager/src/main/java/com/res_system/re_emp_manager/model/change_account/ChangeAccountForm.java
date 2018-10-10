package com.res_system.re_emp_manager.model.change_account;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.model.auth.AuthRAccCooperation;

/**
 * グループアカウント変更画面 フォームクラス.
 * @author res.
 */
public class ChangeAccountForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;
    /** 入力データ. */
    @DataParam
    private AuthRAccCooperation data;
    /** 取得リスト. */
    @ListParam(AuthRAccCooperation.class)
    private List<AuthRAccCooperation> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** グループ識別コード. */
    @Param
    private String code;
    /** ログインID. */
    @Param
    private String id;
    /** パスワード. */
    @Param
    private String key;
    /** ログイン状態保持. */
    @Param
    private String save;

    /** 処理モード. */
    @Param
    private String mode;


    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }
    /** 入力データ を取得します. */
    public AuthRAccCooperation getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(AuthRAccCooperation data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<AuthRAccCooperation> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<AuthRAccCooperation> list) { this.list = list; }
    /** 取得リストサイズ を取得します. */
    public String getList_size() { return list_size; }
    /** 取得リストサイズ を設定します. */
    public void setList_size(String list_size) { this.list_size = list_size; }

    /** グループ識別コード を取得します. */
    public String getCode() { return code; }
    /** グループ識別コード を設定します. */
    public void setCode(String code) { this.code = code; }
    /** ログインID を取得します. */
    public String getId() { return id; }
    /** ログインID を設定します. */
    public void setId(String id) { this.id = id; }
    /** パスワード を取得します. */
    public String getKey() { return key; }
    /** パスワード を設定します. */
    public void setKey(String key) { this.key = key; }
    /** アカウント保存 を取得します. */
    public String getSave() { return save; }
    /** アカウント保存 を設定します. */
    public void setSave(String save) { this.save = save; }

    /** 処理モード を取得します. */
    public String getMode() { return mode; }
    /** 処理モード を設定します. */
    public void setMode(String mode) { this.mode = mode; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public ChangeAccountForm() {
        super();
        searchCond = new SearchCondition();
        data = new AuthRAccCooperation();
        list = new ArrayList<>();
    }

}
