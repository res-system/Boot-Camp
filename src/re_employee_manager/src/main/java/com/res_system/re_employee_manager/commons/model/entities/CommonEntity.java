package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.IEntity;

/**
 * <pre>
 * 共通Entityクラス.
 * </pre>
 * @author res.
 */
public class CommonEntity implements IEntity {

    /** ID. */
    /** アカウントID. */
    private Long id;
    /** 件数. */
    private Integer count;
    /** 区分. */
    private String kbn;

    //-- setter / getter. --//
    /** ID を取得します. */
    public Long getId() { return id; }
    /** ID を設定します. */
    public void setId(Long id) { this.id = id; }
    /** 件数 を取得します. */
    public Integer getCount() { return count; }
    /** 件数 を設定します. */
    public void setCount(Integer count) { this.count = count; }
    /** 区分 を取得します. */
    public String getKbn() { return kbn; }
    /** 区分 を設定します. */
    public void setKbn(String kbn) { this.kbn = kbn; }

}
