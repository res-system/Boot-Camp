package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.SEmpAddr;

/**
 * 社員個人情報管理処理用 社員住所サブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `WK`.`seq` AS `seq`"
            + ",`WK`.`memo` AS `memo`"
            + ",`EMPAD`.`addr_id` AS `addr_id`"
            + ",`ADD`.`postal_code` AS `postal_code`"
            + ",`ADD`.`addr1` AS `addr1`"
            + ",`ADD`.`addr2` AS `addr2`"
            + ",`ADD`.`nearest_station` AS `nearest_station`"
            + " FROM ("
            +   " SELECT 1 AS `seq`, '現住所' AS `memo` FROM dual"
            +   ") `WK`"
            +   " LEFT OUTER JOIN `s_emp_addr` `EMPAD`"
            +       " ON `EMPAD`.`seq` = `WK`.`seq` AND `EMPAD`.`user_id` = ?"
            +   " LEFT OUTER JOIN `g_addr` `ADD`"
            +       " ON `ADD`.`id` = `EMPAD`.`addr_id`"
            )
})
public class EmpInfoSEmpAddr extends SEmpAddr {

    //---------------------------------------------- properies [private].
    /** 郵便番号. */
    @Param
    private String postal_code;
    /** 住所1. */
    @Param
    private String addr1;
    /** 住所2. */
    @Param
    private String addr2;
    /** 最寄り駅. */
    @Param
    private String nearest_station;

    //-- setter / getter. --//
    /** 郵便番号 を取得します. */
    public String getPostal_code() { return postal_code; }
    /** 郵便番号 を設定します. */
    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
    /** 住所1 を取得します. */
    public String getAddr1() { return addr1; }
    /** 住所1 を設定します. */
    public void setAddr1(String addr1) { this.addr1 = addr1; }
    /** 住所2 を取得します. */
    public String getAddr2() { return addr2; }
    /** 住所2 を設定します. */
    public void setAddr2(String addr2) { this.addr2 = addr2; }
    /** 最寄り駅 を取得します. */
    public String getNearest_station() { return nearest_station; }
    /** 最寄り駅 を設定します. */
    public void setNearest_station(String nearest_station) { this.nearest_station = nearest_station; }

}