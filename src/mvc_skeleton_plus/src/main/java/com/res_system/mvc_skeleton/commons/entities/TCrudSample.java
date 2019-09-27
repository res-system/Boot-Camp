package com.res_system.mvc_skeleton.commons.entities;

import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.dao.entities.AtSelect;
import com.res_system.commons.mvc.model.form.Param;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CRUDサンプルテーブル Entityクラス.
 * @author res.
 *  generated at 2019/09/25. 
 */
@Table(name="t_crud_sample", as="TCS")
@Data @Accessors(chain = true)
public class TCrudSample implements IEntity {

    //---------------------------------------------- properies [private].
    /** ID. */
    @Param
    @Key
    @Column
    private String id;
    /** コード. */
    @Param
    @Column
    private String code;
    /** 名称. */
    @Param
    @Column
    private String name;
    /** 備考. */
    @Param
    @Column
    private String memo;
    /** チェックボックス. */
    @Param
    @Column
    private String check;
    /** ラジオボタン. */
    @Param
    @Column
    private String radio;
    /** コンボボックス. */
    @Param
    @Column
    private String select;
    /** 更新日時. */
    @Param
    @Column
    @AtSelect("DATE_FORMAT(`TCS`.`updated`,'%Y/%m/%d %H:%i:%s')")
    private String updated;

}
