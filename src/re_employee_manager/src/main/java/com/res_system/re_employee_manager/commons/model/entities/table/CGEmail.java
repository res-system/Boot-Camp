package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IGenMsEntity;

/**
 * メールアドレスマスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_g_email", as="EML")
public class CGEmail implements IGenMsEntity {

    /** メールアドレスID. */
    @Key
    @Column
    private Long id;
    /** メールアドレス. */
    @Column
    private String email;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** メールアドレスID を取得します. */
    public Long getId() { return id; }
    /** メールアドレスID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** メールアドレス を取得します. */
    public String getEmail() { return email; }
    /** メールアドレス を設定します. */
    public void setEmail(String email) { this.email = email; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
