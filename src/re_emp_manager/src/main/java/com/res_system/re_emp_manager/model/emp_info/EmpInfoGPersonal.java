package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.GPersonal;

/**
 * 社員個人情報管理処理用 個人マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `family_name` = :family_name"
            + ",`first_name` = :first_name"
            + ",`family_name_kana` = :family_name_kana"
            + ",`first_name_kana` = :first_name_kana"
            + ",`sex` = :sex"
            + ",`birthday` = :birthday"
            + ",`mynumber` = :mynumber"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
})
public class EmpInfoGPersonal extends GPersonal {}
