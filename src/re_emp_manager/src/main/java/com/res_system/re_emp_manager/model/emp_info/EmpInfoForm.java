package com.res_system.re_emp_manager.model.emp_info;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.form.DataParam;
import com.res_system.commons.mvc.model.form.ListParam;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.data.SearchCondition;

/**
 * 社員個人情報管理処理 フォームクラス.
 * @author res.
 */
public class EmpInfoForm {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    @DataParam
    private SearchCondition searchCond;

    /** 社員個人情報. */
    @DataParam
    private EmpInfoData data;

    /** 社員メールアドレスリスト. */
    @ListParam(EmpInfoSEmpEmail.class)
    private List<EmpInfoSEmpEmail> empEmailList;
    /** 社員メールアドレスサイズ. */
    @Param
    private String empEmailList_size;

    /** 社員電話番号リスト. */
    @ListParam(EmpInfoSEmpTel.class)
    private List<EmpInfoSEmpTel> empTelList;
    /** 社員電話番号リストサイズ. */
    @Param
    private String empTelList_size;

    /** 社員住所リスト. */
    @ListParam(EmpInfoSEmpAddr.class)
    private List<EmpInfoSEmpAddr> empAddrList;
    /** 社員住所リストサイズ. */
    @Param
    private String empAddrList_size;

    /** 社員サブ情報リスト. */
    @ListParam(EmpInfoSEmpInfo.class)
    private List<EmpInfoSEmpInfo> empInfoList;
    /** 社員サブ情報リストサイズ. */
    @Param
    private String empInfoList_size;


    //-- setter / getter. --//
    /** 検索条件 を取得します. */
    public SearchCondition getSearchCond() { return searchCond; }
    /** 検索条件 を設定します. */
    public void setSearchCond(SearchCondition searchCond) { this.searchCond = searchCond; }

    /** 社員個人情報 を取得します. */
    public EmpInfoData getData() { return data; }
    /** 社員個人情報 を設定します. */
    public void setData(EmpInfoData data) { this.data = data; }

    /** 社員メールアドレスリスト を取得します. */
    public List<EmpInfoSEmpEmail> getEmpEmailList() { return empEmailList; }
    /** 社員メールアドレスリスト を設定します. */
    public void setEmpEmailList(List<EmpInfoSEmpEmail> empEmailList) { this.empEmailList = empEmailList; }
    /** 社員メールアドレスサイズ を取得します. */
    public String getEmpEmailList_size() { return empEmailList_size; }
    /** 社員メールアドレスサイズ を設定します. */
    public void setEmpEmailList_size(String empEmailList_size) { this.empEmailList_size = empEmailList_size; }

    /** 社員電話番号リスト を取得します. */
    public List<EmpInfoSEmpTel> getEmpTelList() { return empTelList; }
    /** 社員電話番号リスト を設定します. */
    public void setEmpTelList(List<EmpInfoSEmpTel> empTelList) { this.empTelList = empTelList; }
    /** 社員電話番号リストサイズ を取得します. */
    public String getEmpTelList_size() { return empTelList_size; }
    /** 社員電話番号リストサイズ を設定します. */
    public void setEmpTelList_size(String empTelList_size) { this.empTelList_size = empTelList_size; }

    /** 社員住所リスト を取得します. */
    public List<EmpInfoSEmpAddr> getEmpAddrList() { return empAddrList; }
    /** 社員住所リスト を設定します. */
    public void setEmpAddrList(List<EmpInfoSEmpAddr> empAddrList) { this.empAddrList = empAddrList; }
    /** 社員住所リストサイズ を取得します. */
    public String getEmpAddrList_size() { return empAddrList_size; }
    /** 社員住所リストサイズ を設定します. */
    public void setEmpAddrList_size(String empAddrList_size) { this.empAddrList_size = empAddrList_size; }

    /** 社員サブ情報リスト を取得します. */
    public List<EmpInfoSEmpInfo> getEmpInfoList() { return empInfoList; }
    /** 社員サブ情報リスト を設定します. */
    public void setEmpInfoList(List<EmpInfoSEmpInfo> empInfoList) { this.empInfoList = empInfoList; }
    /** 社員サブ情報リストサイズ を取得します. */
    public String getEmpInfoList_size() { return empInfoList_size; }
    /** 社員サブ情報リストサイズ を設定します. */
    public void setEmpInfoList_size(String empInfoList_size) { this.empInfoList_size = empInfoList_size; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public EmpInfoForm() {
        super();
        searchCond = new SearchCondition();
        data = new EmpInfoData();
        empEmailList = new ArrayList<>();
        empTelList = new ArrayList<>();
        empAddrList = new ArrayList<>();
        empInfoList = new ArrayList<>();
    }

}
