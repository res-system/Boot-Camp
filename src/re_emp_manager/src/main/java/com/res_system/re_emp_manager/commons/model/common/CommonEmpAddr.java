package com.res_system.re_emp_manager.commons.model.common;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.GAddr;

/**
 * 共通業務処理用 社員住所マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="check_id_emp_addr", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            +   " INNER JOIN `s_emp_addr` `EMPAD` ON `EMPAD`.`addr_id` = `ADD`.`id`"
            + " WHERE `EMPAD`.`user_id` = ? AND `EMPAD`.`seq` = ? AND `EMPAD`.`addr_id` = ?"
            )
})
public class CommonEmpAddr extends GAddr {
}
