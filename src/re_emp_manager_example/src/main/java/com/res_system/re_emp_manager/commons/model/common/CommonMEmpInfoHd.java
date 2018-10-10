package com.res_system.re_emp_manager.commons.model.common;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.MEmpInfoHd;

/**
 * 共通業務処理用 社員情報ヘッダーマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="check_id_emp_info_hd", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `EMPHD`.`root_group_id` = ? AND `EMPHD`.`id` = ?"
            )
})
public class CommonMEmpInfoHd extends MEmpInfoHd {
}
