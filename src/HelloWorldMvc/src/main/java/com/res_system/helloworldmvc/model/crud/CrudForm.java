package com.res_system.helloworldmvc.model.crud;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * Crud用フォームクラス(テスト用)(画面とデータを遣り取りするクラス).
 * </pre>
 * @author res.
 */
public class CrudForm {

    //---------------------------------------------- properies [private].
    @Param
    private String message;
    @DataParam
    private CrudEntity data;
    @ListParam(CrudEntity.class)
    private List<CrudEntity> list;
    @Param
    private String list_size;

    private List<CrudListItem> checkList;
    private List<CrudListItem> radioList;
    private List<CrudListItem> selectList;

    //-- setter / getter. --//
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public CrudEntity getData() { return data; }
    public void setData(CrudEntity data) { this.data = data; }
    public List<CrudEntity> getList() { return list; }
    public void setList(List<CrudEntity> list) { this.list = list; }
    public String getList_size() { return list_size; }
    public void setList_size(String list_size) { this.list_size = list_size; }
    public List<CrudListItem> getCheckList() { return checkList; }
    public void setCheckList(List<CrudListItem> checkList) { this.checkList = checkList; }
    public List<CrudListItem> getRadioList() { return radioList; }
    public void setRadioList(List<CrudListItem> radioList) { this.radioList = radioList; }
    public List<CrudListItem> getSelectList() { return selectList; }
    public void setSelectList(List<CrudListItem> selectList) { this.selectList = selectList; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     * @param message メッセージ.
     */
    public CrudForm() {
        super();
        data = new CrudEntity();
        list = new ArrayList<>();
        checkList = new ArrayList<>();
        radioList = new ArrayList<>();
        selectList = new ArrayList<>();
    }

}
