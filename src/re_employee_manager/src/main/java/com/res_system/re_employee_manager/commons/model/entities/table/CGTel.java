package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IGenMsEntity;

/**
 * 電話番号マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_g_tel", as="TEL")
public class CGTel implements IGenMsEntity {

    /** 電話番号ID. */
    @Key
    @Column
    private Long id;
    /** 電話番号. */
    @Column
    private String tel_no;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 電話番号ID を取得します. */
    public Long getId() { return id; }
    /** 電話番号ID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** 電話番号 を取得します. */
    public String getTel_no() { return tel_no; }
    /** 電話番号 を設定します. */
    public void setTel_no(String tel_no) { this.tel_no = tel_no; }
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
