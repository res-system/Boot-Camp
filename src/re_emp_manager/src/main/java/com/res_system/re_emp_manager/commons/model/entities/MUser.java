package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * ユーザーマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/16. 
 */
@Table(name="m_user", as="USR")
public class MUser implements IEntity {

    //---------------------------------------------- properies [private].
    /** ユーザーID. */
    @Param
    @Key
    @Column
    private String id;
    /** ユーザー名. */
    @Param
    @Column
    private String name;
    /** ユーザー区分. */
    @Param
    @Column
    private String user_kbn;
    /** デフォルト権限ID. */
    @Param
    @Column
    private String default_authority_id;
    /** メールアドレス. */
    @Param
    @Column
    private String email_addr;
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
    public String getId() { return id; }
    /** ユーザーID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ユーザー名 を取得します. */
    public String getName() { return name; }
    /** ユーザー名 を設定します. */
    public void setName(String name) { this.name = name; }
    /** ユーザー区分 を取得します. */
    public String getUser_kbn() { return user_kbn; }
    /** ユーザー区分 を設定します. */
    public void setUser_kbn(String user_kbn) { this.user_kbn = user_kbn; }
    /** デフォルト権限ID を取得します. */
    public String getDefault_authority_id() { return default_authority_id; }
    /** デフォルト権限ID を設定します. */
    public void setDefault_authority_id(String default_authority_id) { this.default_authority_id = default_authority_id; }
    /** メールアドレス を取得します. */
    public String getEmail_addr() { return email_addr; }
    /** メールアドレス を設定します. */
    public void setEmail_addr(String email_addr) { this.email_addr = email_addr; }
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
