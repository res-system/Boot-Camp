package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * 社員情報サブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/08. 
 */
@Table(name="s_emp_info", as="EMPIF")
public class SEmpInfo implements IEntity {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    @Key
    @Column
    private String user_id;
    /** 社員情報ヘッダーID. */
    @Param
    @Key
    @Column
    private String emp_info_hd_id;
    /** 内容. */
    @Param
    @Column
    private String value;
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
    /** 社員情報ヘッダーID を取得します. */
    public String getEmp_info_hd_id() { return emp_info_hd_id; }
    /** 社員情報ヘッダーID を設定します. */
    public void setEmp_info_hd_id(String emp_info_hd_id) { this.emp_info_hd_id = emp_info_hd_id; }
    /** 内容 を取得します. */
    public String getValue() { return value; }
    /** 内容 を設定します. */
    public void setValue(String value) { this.value = value; }
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
