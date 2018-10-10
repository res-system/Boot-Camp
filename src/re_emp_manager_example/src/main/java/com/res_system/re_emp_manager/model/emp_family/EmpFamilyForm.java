package com.res_system.re_emp_manager.model.emp_family;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員家族情報管理処理用 フォームクラス.
 * @author res.
 */
public class EmpFamilyForm {

    //---------------------------------------------- properies [private].
    /** 入力データ. */
    @DataParam
    private EmpFamilyData data;
    /** 取得リスト. */
    @ListParam(EmpFamilyData.class)
    private List<EmpFamilyData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 処理モード. */
    @Param
    private String mode;


    //-- setter / getter. --//
    /** 入力データ を取得します. */
    public EmpFamilyData getData() { return data; }
    /** 入力データ を設定します. */
    public void setData(EmpFamilyData data) { this.data = data; }
    /** 取得リスト を取得します. */
    public List<EmpFamilyData> getList() { return list; }
    /** 取得リスト を設定します. */
    public void setList(List<EmpFamilyData> list) { this.list = list; }
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
    public EmpFamilyForm() {
        super();
        data = new EmpFamilyData();
        list = new ArrayList<>();
    }

}
