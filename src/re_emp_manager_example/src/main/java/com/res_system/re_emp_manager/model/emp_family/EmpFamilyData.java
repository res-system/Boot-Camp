package com.res_system.re_emp_manager.model.emp_family;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.SEmpFamily;

/**
 * 社員家族情報管理処理用 社員家族サブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "--SELECT"
            + ",`PSN`.`family_name` AS `family_name`"
            + ",`PSN`.`first_name` AS `first_name`"
            + ",`PSN`.`family_name_kana` AS `family_name_kana`"
            + ",`PSN`.`first_name_kana` AS `first_name_kana`"
            + ",`PSN`.`sex` AS `sex`"
            + ",`Sex`.`name` AS `sex_name`"
            + ",DATE_FORMAT(`PSN`.`birthday`, '%Y/%m/%d') AS `birthday`"
            + ",`PSN`.`mynumber` AS `mynumber`"
            + ",`Living`.`name` AS `living_name`"
            + " --FROM"
            +   " INNER JOIN `g_personal` `PSN` ON `PSN`.`id` = `EMPFM`.`personal_id`"
            +   " LEFT OUTER JOIN `g_kind` `Sex`"
            +       " ON `Sex`.`kbn` = 'Sex' AND `Sex`.`seq` <> 0 AND `Sex`.`code` = `PSN`.`sex`"
            +   " LEFT OUTER JOIN `g_kind` `Living`"
            +       " ON `Living`.`kbn` = 'Living' AND `Living`.`seq` <> 0 AND `Living`.`code` = `EMPFM`.`living`"
            + " WHERE `EMPFM`.`user_id` = ?"
            + " ORDER BY `seq`"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "--SELECT"
            + ",`PSN`.`family_name` AS `family_name`"
            + ",`PSN`.`first_name` AS `first_name`"
            + ",`PSN`.`family_name_kana` AS `family_name_kana`"
            + ",`PSN`.`first_name_kana` AS `first_name_kana`"
            + ",`PSN`.`sex` AS `sex`"
            + ",`Sex`.`name` AS `sex_name`"
            + ",DATE_FORMAT(`PSN`.`birthday`, '%Y/%m/%d') AS `birthday`"
            + ",`PSN`.`mynumber` AS `mynumber`"
            + ",`Living`.`name` AS `living_name`"
            + " --FROM"
            +   " INNER JOIN `g_personal` `PSN` ON `PSN`.`id` = `EMPFM`.`personal_id`"
            +   " LEFT OUTER JOIN `g_kind` `Sex`"
            +       " ON `Sex`.`kbn` = 'Sex' AND `Sex`.`seq` <> 0 AND `Sex`.`code` = `PSN`.`sex`"
            +   " LEFT OUTER JOIN `g_kind` `Living`"
            +       " ON `Living`.`kbn` = 'Living' AND `Living`.`seq` <> 0 AND `Living`.`code` = `EMPFM`.`living`"
            + " WHERE `EMPFM`.`user_id` = :user_id"
            +   " AND `EMPFM`.`seq` = :seq"
            )
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(`EMPFM`.`seq`) AS `count`"
            + " --FROM"
            + " WHERE `EMPFM`.`user_id` = ?"
            )
    ,@Sql(name="get_max_seq", sql = ""
            + "SELECT IFNULL((MAX(`EMPFM`.`seq`) + 1), 1) AS `max_seq`"
            + " --FROM"
            + " WHERE `EMPFM`.`user_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `relationship` = :relationship "
            + ",`living` = :living "
            + ",`memo` = :memo "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
})
public class EmpFamilyData extends SEmpFamily {

    //---------------------------------------------- properies [private].
    /** 氏名（姓）. */
    @Param
    private String family_name;
    /** 氏名（名）. */
    @Param
    private String first_name;
    /** カナ氏名（姓）. */
    @Param
    private String family_name_kana;
    /** カナ氏名（名）. */
    @Param
    private String first_name_kana;
    /** 性別. */
    @Param
    private String sex;
    /** 性別名. */
    @Param
    private String sex_name;
    /** 生年月日. */
    @Param
    private String birthday;
    /** マイナンバー. */
    @Param
    private String mynumber;
    /** 同居区分名. */
    @Param
    private String living_name;

    //-- setter / getter. --//
    /** 氏名（姓） を取得します. */
    public String getFamily_name() { return family_name; }
    /** 氏名（姓） を設定します. */
    public void setFamily_name(String family_name) { this.family_name = family_name; }
    /** 氏名（名） を取得します. */
    public String getFirst_name() { return first_name; }
    /** 氏名（名） を設定します. */
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    /** カナ氏名（姓） を取得します. */
    public String getFamily_name_kana() { return family_name_kana; }
    /** カナ氏名（姓） を設定します. */
    public void setFamily_name_kana(String family_name_kana) { this.family_name_kana = family_name_kana; }
    /** カナ氏名（名） を取得します. */
    public String getFirst_name_kana() { return first_name_kana; }
    /** カナ氏名（名） を設定します. */
    public void setFirst_name_kana(String first_name_kana) { this.first_name_kana = first_name_kana; }
    /** 性別 を取得します. */
    public String getSex() { return sex; }
    /** 性別 を設定します. */
    public void setSex(String sex) { this.sex = sex; }
    /** 性別名 を取得します. */
    public String getSex_name() { return sex_name; }
    /** 性別名 を設定します. */
    public void setSex_name(String sex_name) { this.sex_name = sex_name; }
    /** 生年月日 を取得します. */
    public String getBirthday() { return birthday; }
    /** 生年月日 を設定します. */
    public void setBirthday(String birthday) { this.birthday = birthday; }
    /** マイナンバー を取得します. */
    public String getMynumber() { return mynumber; }
    /** マイナンバー を設定します. */
    public void setMynumber(String mynumber) { this.mynumber = mynumber; }
    /** 同居区分名 を取得します. */
    public String getLiving_name() { return living_name; }
    /** 同居区分名 を設定します. */
    public void setLiving_name(String living_name) { this.living_name = living_name; }

}
