package com.res_system.mvc_skeleton.model.crud_sample;

import com.res_system.commons.dao.entities.AtInsert;
import com.res_system.commons.dao.entities.AtSelect;
import com.res_system.commons.dao.entities.AtUpdate;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.mvc_skeleton.commons.entities.TCrudSample;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * CRUDサンプル Entityクラス.
 * @author res.
 */
@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class CrudSampleData extends TCrudSample {

    //---------------------------------------------- properies [private].
    /** ID. */
    @Param
    @Key
    @Column(isInsert=false)
    private String id;

    /** 更新日時. */
    @Param
    @Column
    @AtSelect("DATE_FORMAT(`TCS`.`updated`,'%Y/%m/%d %H:%i:%s')")
    @AtInsert("now()")
    @AtUpdate("now()")
    private String updated;

}
