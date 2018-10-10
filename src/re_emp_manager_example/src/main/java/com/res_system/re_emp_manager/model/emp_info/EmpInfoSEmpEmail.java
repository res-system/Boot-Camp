package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.SEmpEmail;

/**
 * 社員個人情報管理処理用 社員メールアドレスサブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `WK`.`seq` AS `seq`"
            + ",`WK`.`memo` AS `memo`"
            + ",`EMPEL`.`email_addr` AS `email_addr`"
            + " FROM ("
            +   " SELECT 1 AS `seq`, 'PC' AS `memo` FROM dual"
            +   " UNION ALL"
            +   " SELECT 2 AS `seq`, '携帯' AS `memo` FROM dual"
            +   ") `WK`"
            +   " LEFT OUTER JOIN `s_emp_email` `EMPEL`"
            +       " ON `EMPEL`.`seq` = `WK`.`seq` AND `EMPEL`.`user_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--INSERT ("
            + " `user_id`"
            + ",`seq`"
            + ",`email_addr`"
            + ",`memo`"
            + ",`updated_id`"
            + ",`created_id`"
            + ") VALUES ("
            + " :user_id"
            + ",:seq"
            + ",:email_addr"
            + ",:memo"
            + ",:updated_id"
            + ",:created_id"
            + ")"
            + " ON DUPLICATE KEY UPDATE"
            + " `email_addr` = :email_addr"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            )
})
public class EmpInfoSEmpEmail extends SEmpEmail {}