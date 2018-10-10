package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.MEmployee;

/**
 * 社員個人情報管理処理用 社員マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `situation` = :situation"
            + ",`employee_no` = :employee_no"
            + ",`hire_date` = :hire_date"
            + ",`retirement_date` = :retirement_date"
            + ",`memo` = :memo"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
})
public class EmpInfoMEmployee extends MEmployee {}