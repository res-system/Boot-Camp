package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * グループアカウントサブマスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/16. 
 */
@Table(name="s_grp_account", as="GRPAC")
public class SGrpAccount implements IEntity {

    //---------------------------------------------- properies [private].
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
    /** デフォルトグループID. */
    @Param
    @Column
    private String default_group_id;
    /** アカウントID. */
    @Param
    @Column
    private String account_id;
    /** ログインID. */
    @Param
    @Column
    private String login_id;
    /** グループアカウント状態. */
    @Param
    @Column
    private String gpac_status;
    /** 登録日. */
    @Param
    @Column
    private String registration_date;
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
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** ユーザーID を取得します. */
    public String getUser_id() { return user_id; }
    /** ユーザーID を設定します. */
    public void setUser_id(String user_id) { this.user_id = user_id; }
    /** デフォルトグループID を取得します. */
    public String getDefault_group_id() { return default_group_id; }
    /** デフォルトグループID を設定します. */
    public void setDefault_group_id(String default_group_id) { this.default_group_id = default_group_id; }
    /** アカウントID を取得します. */
    public String getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(String account_id) { this.account_id = account_id; }
    /** ログインID を取得します. */
    public String getLogin_id() { return login_id; }
    /** ログインID を設定します. */
    public void setLogin_id(String login_id) { this.login_id = login_id; }
    /** グループアカウント状態 を取得します. */
    public String getGpac_status() { return gpac_status; }
    /** グループアカウント状態 を設定します. */
    public void setGpac_status(String gpac_status) { this.gpac_status = gpac_status; }
    /** 登録日 を取得します. */
    public String getRegistration_date() { return registration_date; }
    /** 登録日 を設定します. */
    public void setRegistration_date(String registration_date) { this.registration_date = registration_date; }
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
