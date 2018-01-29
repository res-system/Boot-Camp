package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Date;
import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IGenMsEntity;

/**
 * 個人マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_g_personal", as="PSN")
public class CGPersonal implements IGenMsEntity {

    /** 個人ID. */
    @Key
    @Column
    private Long id;
    /** 氏名（姓）. */
    @Column
    private String family_name;
    /** 氏名（名）. */
    @Column
    private String first_name;
    /** カナ氏名（姓）. */
    @Column
    private String family_name_kana;
    /** カナ氏名（名）. */
    @Column
    private String first_name_kana;
    /** 旧姓. */
    @Column
    private String maiden_name;
    /** カナ旧姓. */
    @Column
    private String maiden_name_kana;
    /** イニシャル. */
    @Column
    private String initial;
    /** 性別. */
    @Column
    private String sex;
    /** 生年月日. */
    @Column
    private Date birthday;
    /** マイナンバー. */
    @Column
    private String mynumber;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 個人ID を取得します. */
    public Long getId() { return id; }
    /** 個人ID を設定します. */
    public void setId(Long id) { this.id = id; }
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
    public Date getBirthday() { return birthday; }
    /** 生年月日 を設定します. */
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    /** マイナンバー を取得します. */
    public String getMynumber() { return mynumber; }
    /** マイナンバー を設定します. */
    public void setMynumber(String mynumber) { this.mynumber = mynumber; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
