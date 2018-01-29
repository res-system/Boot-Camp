package com.res_system.re_employee_manager.model.employee_search;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.MEmployee;

/**
 * 社員情報検索[ 一覧 ]画面用 社員マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="update_group_id",
           sql = "UPDATE `m_employee` SET"
                   + " `default_group_id`=:default_group_id"
                   + ",`updated_id`=:updated_id"
                   + ",`updated`=CURRENT_TIMESTAMP"
                   + " WHERE `id`=:id")
})
public class EmpSearchMEmployee extends MEmployee {}
