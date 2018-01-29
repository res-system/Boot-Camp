package com.res_system.re_employee_manager.model.employee_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.MEmployee;

/**
 * 社員個人情報管理用 社員マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="update_employee_info",
           sql = "UPDATE `m_employee` SET"
                   + " `employee_no`=:employee_no"
                   + ",`emp_status`=:emp_status"
                   + ",`position`=:position"
                   + ",`joining_date`=:joining_date"
                   + ",`leaving_date`=:leaving_date"
                   + ",`acquisition_date`=:acquisition_date"
                   + ",`insurance_no`=:insurance_no"
                   + ",`pension_no`=:pension_no"
                   + ",`updated_id`=:updated_id"
                   + ",`updated`=CURRENT_TIMESTAMP"
                   + " WHERE `id`=:id")
   })
public class EmpInfoMEmployee extends MEmployee {
}
