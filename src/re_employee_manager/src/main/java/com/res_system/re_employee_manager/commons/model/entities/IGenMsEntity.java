package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.IEntity;

/**
 * 汎用マスタEntityインターフェース.
 * @author res.
 */
public interface IGenMsEntity extends IEntity {

    /** ID を取得します. */
    Long getId();
    /** ID を設定します. */
    void setId(Long id);
    /** 更新者ID を取得します. */
    Long getUpdated_id();
    /** 更新者ID を設定します. */
    void setUpdated_id(Long updated_id);

}
