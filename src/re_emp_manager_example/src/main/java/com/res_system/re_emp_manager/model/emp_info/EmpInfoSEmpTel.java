package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.SEmpTel;

/**
 * 社員個人情報管理処理用 社員電話番号サブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `WK`.`seq` AS `seq`"
            + ",`WK`.`memo` AS `memo`"
            + ",`EMPTL`.`tel_no` AS `tel_no`"
            + " FROM ("
            +   " SELECT 1 AS `seq`, '自宅' AS `memo` FROM dual"
            +   " UNION ALL"
            +   " SELECT 2 AS `seq`, '携帯' AS `memo` FROM dual"
            +   ") `WK`"
            +   " LEFT OUTER JOIN `s_emp_tel` `EMPTL`"
            +       " ON `EMPTL`.`seq` = `WK`.`seq` AND `EMPTL`.`user_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--INSERT ("
            + " `user_id`"
            + ",`seq`"
            + ",`tel_no`"
            + ",`memo`"
            + ",`updated_id`"
            + ",`created_id`"
            + ") VALUES ("
            + " :user_id"
            + ",:seq"
            + ",:tel_no"
            + ",:memo"
            + ",:updated_id"
            + ",:created_id"
            + ")"
            + " ON DUPLICATE KEY UPDATE"
            + " `tel_no` = :tel_no"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            )
})
public class EmpInfoSEmpTel extends SEmpTel {}
