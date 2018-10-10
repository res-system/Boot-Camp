package com.res_system.re_emp_manager.commons.model.common;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.GPersonal;

/**
 * 共通業務処理用 社員個人マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="check_id_emp_personal", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            +   " INNER JOIN `m_employee` `EMP` ON `EMP`.`personal_id` = `PSN`.`id`"
            + " WHERE `EMP`.`user_id` = ? AND `EMP`.`personal_id` = ?"
            )
    ,@Sql(name="check_id_fml_personal", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            +   " INNER JOIN `s_emp_family` `EMPFM` ON `EMPFM`.`personal_id` = `PSN`.`id`"
            + " WHERE `EMPFM`.`user_id` = ? AND `EMPFM`.`seq` = ? AND `EMPFM`.`personal_id` = ?"
            )
})
public class CommonEmpPersonal extends GPersonal {
}
