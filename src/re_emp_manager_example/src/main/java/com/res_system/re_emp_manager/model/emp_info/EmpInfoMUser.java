package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.MUser;

/**
 * 社員個人情報管理処理用 ユーザーマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `name` = :name"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
})
public class EmpInfoMUser extends MUser {}