package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.CRAuthGrant;

/**
 * 認証処理用 権限付与関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_by_account_id",
            sql = "SELECT `AUGT`.`kbn` AS `kbn`"
                    + " FROM `c_m_account` `ACC`"
                    +   " INNER JOIN `c_m_authority`  `AUT`  ON `ACC`.`default_authority_id`=`AUT`.`id`  AND `AUT`.`status`=0"
                    +   " INNER JOIN `c_r_auth_grant` `AUGT` ON `AUT`.`id`=`AUGT`.`authority_id`"
                    +   " INNER JOIN `c_m_permission` `PER`  ON `AUGT`.`permission_id`=`PER`.`id` AND `PER`.`status`=0 AND `PER`.`key`=?"
                    + " WHERE `ACC`.`id` = ?")
    ,@Sql(name="find_by_group_id_and_account_id",
            sql = "SELECT `AUGT`.`kbn` AS `kbn`"
                    + " FROM `c_r_grp_account` `GPAC`"
                    +   " INNER JOIN `c_m_authority`  `AUT`  ON `GPAC`.`group_authority_id`=`AUT`.`id`  AND `AUT`.`status`=0"
                    +   " INNER JOIN `c_r_auth_grant` `AUGT` ON `AUT`.`id`=`AUGT`.`authority_id`"
                    +   " INNER JOIN `c_m_permission` `PER`  ON `AUGT`.`permission_id`=`PER`.`id` AND `PER`.`status`=0 AND `PER`.`key`=?"
                    + " WHERE `GPAC`.`group_id` = ? AND `GPAC`.`account_id` = ?")
})
public class AuthCRAuthGrant extends CRAuthGrant {
}
