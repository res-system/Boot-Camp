package com.res_system.re_employee_manager.model.password_change;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.CSAcctKey;

/**
 * パスワード変更用 Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_password",
           sql = "SELECT--*"
                   + ",`ACC`.`salt` AS `salt`"
                   + " FROM--*"
                   +    " INNER JOIN `c_m_account` `ACC` ON `ACKY`.`account_id`=`ACC`.`id`"
                   + " WHERE `ACKY`.`account_id`=:account_id AND `ACKY`.`seq`=:seq")
    ,@Sql(name="update_password",
            sql = "UPDATE `c_s_acct_key` SET"
                    + " `key`=:key"
                    + ",`expiration_time`=:expiration_time"
                    + ",`updated_id`=:updated_id"
                    + ",`updated`=CURRENT_TIMESTAMP"
                    + " WHERE `account_id`=:account_id AND `seq`=:seq")
   })
public class PasswordChangeEntity extends CSAcctKey {

    /** ソルト. */
    private String salt;

    //-- setter / getter. --//
    /** ソルト を取得します. */
    public String getSalt() { return salt; }
    /** ソルト を設定します. */
    public void setSalt(String salt) { this.salt = salt; }

}
