package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.RGrant;

/**
 * 認証処理用 権限付与関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_permission_id", sql = ""
            + "SELECT"
            + " `GRT`.`permission_id` AS `permission_id`"
            + " --FROM"
            +   " INNER JOIN `m_permission` `PER` ON `PER`.`id` = `GRT`.`permission_id`"
            + " WHERE `GRT`.`authority_id` = ?"
            +   " AND `PER`.`key` = ?"
            +   " AND (`GRT`.`kbn` IS NULL OR `GRT`.`kbn` = ?)"
            )
})
public class AuthRGrant extends RGrant {
}
