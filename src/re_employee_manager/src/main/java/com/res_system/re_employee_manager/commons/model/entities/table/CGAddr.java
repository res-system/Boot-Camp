package com.res_system.re_employee_manager.commons.model.entities.table;

import java.sql.Timestamp;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.re_employee_manager.commons.model.entities.IGenMsEntity;

/**
 * 住所マスタ Entityクラス.
 * @author res.
 *  generated at 2018/01/04.
 */
@Table(name="c_g_addr", as="ADD")
public class CGAddr implements IGenMsEntity {

    /** 住所ID. */
    @Key
    @Column
    private Long id;
    /** 郵便番号. */
    @Column
    private String postal_code;
    /** 住所1. */
    @Column
    private String addr1;
    /** 住所2. */
    @Column
    private String addr2;
    /** 最寄り駅. */
    @Column
    private String nearest_station;
    /** 更新者ID. */
    @Column
    private Long updated_id;
    /** 更新日時. */
    @Column(isSelect=false,isInsert=false,isUpdate=false)
    private Timestamp updated;

    //-- setter / getter. --//
    /** 住所ID を取得します. */
    public Long getId() { return id; }
    /** 住所ID を設定します. */
    public void setId(Long id) { this.id = id; }
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
    /** 更新者ID を取得します. */
    public Long getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(Long updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public Timestamp getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(Timestamp updated) { this.updated = updated; }

}
