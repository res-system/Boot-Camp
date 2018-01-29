package com.res_system.re_employee_manager.commons.model.entities;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;

/**
 * <pre>
 * ログイン情報 Entityクラス.
 * </pre>
 * @author res.
 */
@Sqls({
     @Sql(name="find_by_login_id",
             sql = "SELECT"
                     + " '' AS `token`"
                     + ",NULL AS `selected_group_id`"
                     + ",'' AS `selected_group_name`"
                     + ",`ACKY`.`key` AS `password`"
                     + ",`ACC`.`salt` AS `salt`"
                     + ",`ACKY`.`expiration_time` AS `expiration_time`"
                     + ",`ACC`.`id` AS `account_id`"
                     + ",`ACC`.`name` AS `account_name`"
                     + ",`ACC`.`default_authority_id` AS `default_authority_id`"
                     + ",`AUT`.`name` AS `default_authority_name`"
                     + ",`ACC`.`acc_status` AS `acc_status`"
                     + ",`AcStat`.`name` AS `acc_status_name`"
                     + " FROM `c_m_account` `ACC`"
                     +   " INNER JOIN `c_s_acct_key`       `ACKY`   ON `ACC`.`id`                  =`ACKY`.`account_id` AND `ACKY`.`seq`=1"
                     +   " LEFT OUTER JOIN `c_m_authority` `AUT`    ON `ACC`.`default_authority_id`=`AUT`.`id`"
                     +   " LEFT OUTER JOIN `c_g_kind`      `AcStat` ON `ACC`.`acc_status`          =`AcStat`.`code` AND `AcStat`.`seq`<>0 AND `AcStat`.`kbn`='AcStat'"
                     + " WHERE NOT(`ACC`.`acc_status` = '80' OR `ACC`.`acc_status` = '90') AND `ACC`.`login_id` = ?")
     ,@Sql(name="find_by_token",
             sql = "SELECT"
                     + " `LGN`.`token` AS `token`"
                     + ",`LGN`.`selected_group_id` AS `selected_group_id`"
                     + ",`GRP`.`name` AS `selected_group_name`"
                     + ",'' AS `password`"
                     + ",'' AS `salt`"
                     + ",`ACKY`.`expiration_time` AS `expiration_time`"
                     + ",`ACC`.`id` AS `account_id`"
                     + ",`ACC`.`name` AS `account_name`"
                     + ",`ACC`.`default_authority_id` AS `default_authority_id`"
                     + ",`AUT`.`name` AS `default_authority_name`"
                     + ",`ACC`.`acc_status` AS `acc_status`"
                     + ",`AcStat`.`name` AS `acc_status_name`"
                     + " FROM `c_m_account` `ACC`"
                     +   " INNER JOIN `c_t_login`          `LGN`    ON `ACC`.`id` =`LGN`.`account_id` AND `LGN`.`expiration_time` >= now() AND `LGN`.`kbn` = '00'"
                     +   " INNER JOIN `c_s_acct_key`       `ACKY`   ON `ACC`.`id` =`ACKY`.`account_id` AND `ACKY`.`seq`=1"
                     +   " LEFT OUTER JOIN `c_m_authority` `AUT`    ON `ACC`.`default_authority_id`=`AUT`.`id`"
                     +   " LEFT OUTER JOIN `c_m_group`     `GRP`    ON `LGN`.`selected_group_id`   =`GRP`.`id`"
                     +   " LEFT OUTER JOIN `c_g_kind`      `AcStat` ON `ACC`.`acc_status`          =`AcStat`.`code` AND `AcStat`.`seq`<>0 AND `AcStat`.`kbn`='AcStat'"
                     + " WHERE NOT(`ACC`.`acc_status` = '80' OR `ACC`.`acc_status` = '90') AND `LGN`.`token` = ?")
})
public class LoginInfo implements IEntity {

    //---------------------------------------------- properies [private].
    /** 認証トークン. */
    private String token;
    /** 選択グループID. */
    private Long selected_group_id;
    /** 選択グループ名. */
    private String selected_group_name;
    /** パスワード. */
    private String password;
    /** ソルト. */
    private String salt;
    /** 有効期限. */
    private Timestamp expiration_time;
    /** アカウントID. */
    private Long account_id;
    /** アカウント名. */
    private String account_name;
    /** デフォルト権限ID. */
    private Long default_authority_id;
    /** デフォルト権限名称. */
    private String default_authority_name;
    /** 状態. */
    private String acc_status;
    /** 状態名称. */
    private String acc_status_name;

    /** 認証情報保存フラグ. */
    private boolean isSaveAuth;


    //-- setter / getter. --//
    /** 認証トークン を取得します. */
    public String getToken() { return token; }
    /** 認証トークン を設定します. */
    public void setToken(String token) { this.token = token; }
    /** 選択グループID を取得します. */
    public Long getSelected_group_id() { return selected_group_id; }
    /** 選択グループID を設定します. */
    public void setSelected_group_id(Long selected_group_id) { this.selected_group_id = selected_group_id; }
    /** 選択グループ名 を取得します. */
    public String getSelected_group_name() { return selected_group_name; }
    /** 選択グループ名 を設定します. */
    public void setSelected_group_name(String selected_group_name) { this.selected_group_name = selected_group_name; }
    /** パスワード を取得します. */
    public String getPassword() { return password; }
    /** パスワード を設定します. */
    public void setPassword(String password) { this.password = password; }
    /** ソルト を取得します. */
    public String getSalt() { return salt; }
    /** ソルト を設定します. */
    public void setSalt(String salt) { this.salt = salt; }
    /** 有効期限 を取得します. */
    public Timestamp getExpiration_time() { return expiration_time; }
    /** 有効期限 を設定します. */
    public void setExpiration_time(Timestamp expiration_time) { this.expiration_time = expiration_time; }
    /** アカウントID を取得します. */
    public Long getAccount_id() { return account_id; }
    /** アカウントID を設定します. */
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
    /** アカウント名 を取得します. */
    public String getAccount_name() { return account_name; }
    /** アカウント名 を設定します. */
    public void setAccount_name(String account_name) { this.account_name = account_name; }
    /** デフォルト権限ID を取得します. */
    public Long getDefault_authority_id() { return default_authority_id; }
    /** デフォルト権限ID を設定します. */
    public void setDefault_authority_id(Long default_authority_id) { this.default_authority_id = default_authority_id; }
    /** デフォルト権限名称 を取得します. */
    public String getDefault_authority_name() { return default_authority_name; }
    /** デフォルト権限名称 を設定します. */
    public void setDefault_authority_name(String default_authority_name) { this.default_authority_name = default_authority_name; }
    /** 状態 を取得します. */
    public String getAcc_status() { return acc_status; }
    /** 状態 を設定します. */
    public void setAcc_status(String acc_status) { this.acc_status = acc_status; }
    /** 状態名称 を取得します. */
    public String getAcc_status_name() { return acc_status_name; }
    /** 状態名称 を設定します. */
    public void setAcc_status_name(String acc_status_name) { this.acc_status_name = acc_status_name; }

    /** 認証情報保存フラグ を取得します. */
    public boolean isSaveAuth() { return isSaveAuth; }
    /** 認証情報保存フラグ を設定します. */
    public void setSaveAuth(boolean isSaveAuth) { this.isSaveAuth = isSaveAuth; }

}
