package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * アカウント連携関連マスタ Entityクラス.
 * @author res.
 *  generated at 2018/09/21. 
 */
@Table(name="r_acc_cooperation", as="ACCOP")
public class RAccCooperation implements IEntity {

    //---------------------------------------------- properies [private].
    /** アカウントID. */
    @Param
    @Key
    @Column
    private String account_id;
    /** ルートグループID. */
    @Param
    @Key
    @Column
    private String root_group_id;
    /** ユーザーID. */
    @Param
    @Key
    @Column
    private String user_id;
    /** 保存フラグ. */
    @Param
    @Column
    private String save_flg;
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
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** 保存フラグ を取得します. */
    public String getSave_flg() { return save_flg; }
    /** 保存フラグ を設定します. */
    public void setSave_flg(String save_flg) { this.save_flg = save_flg; }
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
