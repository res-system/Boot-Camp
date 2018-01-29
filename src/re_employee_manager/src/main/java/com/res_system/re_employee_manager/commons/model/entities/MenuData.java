package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;

/**
 * <pre>
 * メニュー情報クラス.
 * </pre>
 * @author res.
 */
@Sqls({
    @Sql(name="find_list_by_account_id",
            sql = "SELECT DISTINCT"
                    + " `PER`.`key` AS `key`"
                    + ",`PER`.`default_url` AS `url`"
                    + ",`PER`.`name` AS `name`"
                    + ",`PER`.`memo` AS `memo`"
                    + ",`AUGT`.`kbn` AS `kbn`"
                    + ",`AUGT`.`seq` AS `seq`"
                    + " FROM `c_m_account` `ACC`"
                    +   " INNER JOIN `c_m_authority`  `AUT`  ON `ACC`.`default_authority_id`=`AUT`.`id`  AND `AUT`.`status`=0"
                    +   " INNER JOIN `c_r_auth_grant` `AUGT` ON `AUT`.`id`=`AUGT`.`authority_id`"
                    +   " INNER JOIN `c_m_permission` `PER`  ON `AUGT`.`permission_id`=`PER`.`id` AND `PER`.`status`=0 AND (`PER`.`flg`&?)<>0"
                    + " WHERE `ACC`.`id` = ?"
                    + " ORDER BY `AUGT`.`seq`,`PER`.`updated` DESC")
    ,@Sql(name="find_list_by_group_id_and_account_id",
            sql = "SELECT DISTINCT"
                    + " `PER`.`key` AS `key`"
                    + ",`PER`.`default_url` AS `url`"
                    + ",`PER`.`name` AS `name`"
                    + ",`PER`.`memo` AS `memo`"
                    + ",`AUGT`.`kbn` AS `kbn`"
                    + ",`AUGT`.`seq` AS `seq`"
                    + " FROM `c_r_grp_account` `GPAC`"
                    +   " INNER JOIN `c_m_authority`  `AUT`  ON `GPAC`.`group_authority_id`=`AUT`.`id`  AND `AUT`.`status`=0"
                    +   " INNER JOIN `c_r_auth_grant` `AUGT` ON `AUT`.`id`=`AUGT`.`authority_id`"
                    +   " INNER JOIN `c_m_permission` `PER`  ON `AUGT`.`permission_id`=`PER`.`id` AND `PER`.`status`=0 AND (`PER`.`flg`&?)<>0"
                    + " WHERE `GPAC`.`group_id` = ? AND `GPAC`.`account_id` = ?"
                    + " ORDER BY `AUGT`.`seq`,`PER`.`updated` DESC")
})
public class MenuData implements IEntity {

    //---------------------------------------------- properies [private].
    /** 機能. */
    @Column
    private String key;
    /** URL. */
    @Column
    private String url;
    /** 名称. */
    @Column
    private String name;
    /** 備考. */
    @Column
    private String memo;
    /** 連番. */
    @Column
    private Integer seq;
    /** 区分. */
    @Column
    private String kbn;

    //-- setter / getter. --//
    /** 機能 を取得します. */
    public String getKey() { return key; }
    /** 機能 を設定します. */
    public void setKey(String key) { this.key = key; }
    /** URL を取得します. */
    public String getUrl() { return url; }
    /** URL を設定します. */
    public void setUrl(String url) { this.url = url; }
    /** 名称 を取得します. */
    public String getName() { return name; }
    /** 名称 を設定します. */
    public void setName(String name) { this.name = name; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }
    /** 連番 を取得します. */
    public Integer getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(Integer seq) { this.seq = seq; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }

}
