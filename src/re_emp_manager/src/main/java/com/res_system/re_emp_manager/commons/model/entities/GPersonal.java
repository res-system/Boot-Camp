package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 個人マスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="g_personal", as="PSN")
public class GPersonal implements IEntity {

    //---------------------------------------------- properies [private].
    /** 個人ID. */
    @Param
    @Key
    @Column
    private String id;
    /** 氏名（姓）. */
    @Param
    @Column
    private String family_name;
    /** 氏名（名）. */
    @Param
    @Column
    private String first_name;
    /** カナ氏名（姓）. */
    @Param
    @Column
    private String family_name_kana;
    /** カナ氏名（名）. */
    @Param
    @Column
    private String first_name_kana;
    /** 旧姓. */
    @Param
    @Column
    private String maiden_name;
    /** カナ旧姓. */
    @Param
    @Column
    private String maiden_name_kana;
    /** イニシャル. */
    @Param
    @Column
    private String initial;
    /** 性別. */
    @Param
    @Column
    private String sex;
    /** 生年月日. */
    @Param
    @Column
    private String birthday;
    /** マイナンバー. */
    @Param
    @Column
    private String mynumber;
    /** 更新者ID. */
    @Param
    @Column
    private String updated_id;
    /** 更新日時. */
    @Param
    @Column(isInsert=false)
    private String updated;
    /** 作成者ID. */
    @Param
    @Column
    private String created_id;
    /** 作成日時. */
    @Param
    @Column(isInsert=false,isUpdate=false)
    private String created;

    //-- setter / getter. --//
    /** 個人ID を取得します. */
    public String getId() { return id; }
    /** 個人ID を設定します. */
    public void setId(String id) { this.id = id; }
    /** 氏名（姓） を取得します. */
    public String getFamily_name() { return family_name; }
    /** 氏名（姓） を設定します. */
    public void setFamily_name(String family_name) { this.family_name = family_name; }
    /** 氏名（名） を取得します. */
    public String getFirst_name() { return first_name; }
    /** 氏名（名） を設定します. */
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    /** カナ氏名（姓） を取得します. */
    public String getFamily_name_kana() { return family_name_kana; }
    /** カナ氏名（姓） を設定します. */
    public void setFamily_name_kana(String family_name_kana) { this.family_name_kana = family_name_kana; }
    /** カナ氏名（名） を取得します. */
    public String getFirst_name_kana() { return first_name_kana; }
    /** カナ氏名（名） を設定します. */
    public void setFirst_name_kana(String first_name_kana) { this.first_name_kana = first_name_kana; }
    /** 旧姓 を取得します. */
    public String getMaiden_name() { return maiden_name; }
    /** 旧姓 を設定します. */
    public void setMaiden_name(String maiden_name) { this.maiden_name = maiden_name; }
    /** カナ旧姓 を取得します. */
    public String getMaiden_name_kana() { return maiden_name_kana; }
    /** カナ旧姓 を設定します. */
    public void setMaiden_name_kana(String maiden_name_kana) { this.maiden_name_kana = maiden_name_kana; }
    /** イニシャル を取得します. */
    public String getInitial() { return initial; }
    /** イニシャル を設定します. */
    public void setInitial(String initial) { this.initial = initial; }
    /** 性別 を取得します. */
    public String getSex() { return sex; }
    /** 性別 を設定します. */
    public void setSex(String sex) { this.sex = sex; }
    /** 生年月日 を取得します. */
    public String getBirthday() { return birthday; }
    /** 生年月日 を設定します. */
    public void setBirthday(String birthday) { this.birthday = birthday; }
    /** マイナンバー を取得します. */
    public String getMynumber() { return mynumber; }
    /** マイナンバー を設定します. */
    public void setMynumber(String mynumber) { this.mynumber = mynumber; }
    /** 更新者ID を取得します. */
    public String getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(String updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public String getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(String updated) { this.updated = updated; }
    /** 作成者ID を取得します. */
    public String getCreated_id() { return created_id; }
    /** 作成者ID を設定します. */
    public void setCreated_id(String created_id) { this.created_id = created_id; }
    /** 作成日時 を取得します. */
    public String getCreated() { return created; }
    /** 作成日時 を設定します. */
    public void setCreated(String created) { this.created = created; }

}
