package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員家族サブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="s_emp_family", as="EMPFM")
public class SEmpFamily implements IEntity {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    @Key
    @Column
    private String user_id;
    /** 連番. */
    @Param
    @Key
    @Column
    private String seq;
    /** 続柄. */
    @Param
    @Column
    private String relationship;
    /** 個人ID. */
    @Param
    @Column
    private String personal_id;
    /** 住所ID. */
    @Param
    @Column
    private String addr_id;
    /** 電話番号. */
    @Param
    @Column
    private String tel_no;
    /** メールアドレス. */
    @Param
    @Column
    private String email_addr;
    /** 同居区分. */
    @Param
    @Column
    private String living;
    /** 備考. */
    @Param
    @Column
    private String memo;
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
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** 連番 を取得します. */
    public String getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(String seq) { this.seq = seq; }
    /** 続柄 を取得します. */
    public String getRelationship() { return relationship; }
    /** 続柄 を設定します. */
    public void setRelationship(String relationship) { this.relationship = relationship; }
    /** 個人ID を取得します. */
    public String getPersonal_id() { return personal_id; }
    /** 個人ID を設定します. */
    public void setPersonal_id(String personal_id) { this.personal_id = personal_id; }
    /** 住所ID を取得します. */
    public String getAddr_id() { return addr_id; }
    /** 住所ID を設定します. */
    public void setAddr_id(String addr_id) { this.addr_id = addr_id; }
    /** 電話番号 を取得します. */
    public String getTel_no() { return tel_no; }
    /** 電話番号 を設定します. */
    public void setTel_no(String tel_no) { this.tel_no = tel_no; }
    /** メールアドレス を取得します. */
    public String getEmail_addr() { return email_addr; }
    /** メールアドレス を設定します. */
    public void setEmail_addr(String email_addr) { this.email_addr = email_addr; }
    /** 同居区分 を取得します. */
    public String getLiving() { return living; }
    /** 同居区分 を設定します. */
    public void setLiving(String living) { this.living = living; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
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
