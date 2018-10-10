package com.res_system.re_emp_manager.commons.model.common;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.SGrpAccount;

/**
 * 共通業務処理用 グループアカウントサブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="check_id_user", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRPAC`.`root_group_id` = ? AND `GRPAC`.`user_id` = ?"
            )
    ,@Sql(name="check_owner_by_user_id", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            +   " INNER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `GRPAC`.`root_group_id` AND `GRP`.`owner_account_id` = `GRPAC`.`account_id`"
            +   " INNER JOIN `m_account` `ACC`"
            +       " ON `ACC`.`id` = `GRP`.`owner_account_id`"
            + " WHERE `GRPAC`.`root_group_id` = ? AND `GRPAC`.`user_id` = ?"
            )
})
public class CommonSGrpAccount extends SGrpAccount {
}
