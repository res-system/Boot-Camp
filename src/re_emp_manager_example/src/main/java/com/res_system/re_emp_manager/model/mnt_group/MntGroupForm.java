package com.res_system.re_emp_manager.model.mnt_group;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;

/**
 * グループ情報メンテナンス処理 フォームクラス.
 * @author res.
 */
public class MntGroupForm {

    //---------------------------------------------- properies [private].
    /** 入力データ. */
    @DataParam
    private MntGroupData data;
    /** 取得リスト. */
    @ListParam(MntGroupMemberData.class)
    private List<MntGroupMemberData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 処理モード. */
    @Param
    private String mode;


    //-- setter / getter. --//
    /** 入力データ を取得します. */
    public MntGroupData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(MntGroupData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<MntGroupMemberData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<MntGroupMemberData> list) { this.list = list; }
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
    public MntGroupForm() {
        super();
        data = new MntGroupData();
        list = new ArrayList<>();
    }

}
