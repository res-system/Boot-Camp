package com.res_system.mvc_skeleton.model.crud_sample;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CRUDサンプル フォームクラス.
 * @author res.
 */
@Data @Accessors(chain = true)
public class CrudSampleForm {

    //---------------------------------------------- properies [private].
    /** 取得リスト. */
    @ListParam(CrudSampleData.class)
    private List<CrudSampleData> list;
    /** 取得リストサイズ. */
    @Param
    private String list_size;

    /** 入力データ. */
    @DataParam
    private CrudSampleData data;

    /** 処理モード. */
    @Param
    private String mode;



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public CrudSampleForm() {
        super();
        list = new ArrayList<>();
        data = new CrudSampleData();
    }

}
