package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * ユーザーキーサブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="s_user_key", as="USRKY")
public class SUserKey implements IEntity {

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
    /** キー. */
    @Param
    @Column
    private String key;
    /** 有効期限. */
    @Param
    @Column
    private String expiration_time;
    /** 初期値フラグ. */
    @Param
    @Column
    private String initial_flg;
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
    /** キー を取得します. */
    public String getKey() { return key; }
    /** キー を設定します. */
    public void setKey(String key) { this.key = key; }
    /** 有効期限 を取得します. */
    public String getExpiration_time() { return expiration_time; }
    /** 有効期限 を設定します. */
    public void setExpiration_time(String expiration_time) { this.expiration_time = expiration_time; }
    /** 初期値フラグ を取得します. */
    public String getInitial_flg() { return initial_flg; }
    /** 初期値フラグ を設定します. */
    public void setInitial_flg(String initial_flg) { this.initial_flg = initial_flg; }
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
