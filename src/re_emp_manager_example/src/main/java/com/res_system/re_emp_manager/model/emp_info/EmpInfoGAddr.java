package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.GAddr;

/**
 * 社員個人情報管理処理用 住所マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `postal_code` = :postal_code"
            + ",`addr1` = :addr1"
            + ",`addr2` = :addr2"
            + ",`nearest_station` = :nearest_station"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " --WHERE"
            )
})
public class EmpInfoGAddr extends GAddr {}