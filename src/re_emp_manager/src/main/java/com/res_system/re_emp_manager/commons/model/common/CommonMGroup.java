package com.res_system.re_emp_manager.commons.model.common;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_emp_manager.commons.model.entities.MGroup;

/**
 * 共通業務処理用 グループマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="check_id_group", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRP`.`id` = ? AND `GRP`.`root_group_id` = ?"
            )
})
public class CommonMGroup extends MGroup {
}
