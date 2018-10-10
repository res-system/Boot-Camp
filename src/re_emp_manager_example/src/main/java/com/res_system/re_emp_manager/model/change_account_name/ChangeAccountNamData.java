package com.res_system.re_emp_manager.model.change_account_name;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.re_emp_manager.commons.model.entities.MUser;

/**
 * アカウント名変更画面処理 データクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "--SELECT"
            + " --FROM"
            +   " INNER JOIN `m_account` `ACC`"
            +       " ON `ACC`.`user_id` = `USR`.`id` AND `USR`.`user_kbn` = '2' AND `ACC`.`acc_status` IN('0', '1')"
            + " WHERE `ACC`.`id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
             + "--UPDATE"
             + " `name` = :name"
             + ",`updated_id` = :updated_id"
             + ",`updated` = CURRENT_TIMESTAMP"
             + " --WHERE"
             )
})
public class ChangeAccountNamData extends MUser  {}
