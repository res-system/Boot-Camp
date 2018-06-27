package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.CTLogin;

/**
 * 認証処理用 ログイン認証トラン Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_login_token_by_account_id",
            sql = "--SELECT --FROM"
                    + " WHERE `LGN`.`kbn` = '00' AND `LGN`.`account_id` = ?")
    ,@Sql(name="find_check_data",
            sql = "SELECT `LGN`.`account_id`, `LGN`.`save_flg` --FROM"
                    + " WHERE `LGN`.`kbn` = '00' AND `LGN`.`expiration_time` >= now() AND `LGN`.`token` = ?")
    ,@Sql(name="update_group",
            sql = "UPDATE `c_t_login` SET `selected_group_id` = :selected_group_id"
                    + " WHERE `token` = :token")
})
public class AuthCTLogin extends CTLogin {
}
