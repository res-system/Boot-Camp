package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員メールアドレスサブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="s_emp_email", as="EMPEL")
public class SEmpEmail implements IEntity {

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
    /** メールアドレス. */
    @Param
    @Column
    private String email_addr;
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
    /** メールアドレス を取得します. */
    public String getEmail_addr() { return email_addr; }
    /** メールアドレス を設定します. */
    public void setEmail_addr(String email_addr) { this.email_addr = email_addr; }
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
