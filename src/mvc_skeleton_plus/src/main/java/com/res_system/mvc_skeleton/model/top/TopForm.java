package com.res_system.mvc_skeleton.model.top;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TOP画面 フォームクラス.
 * @author res.
 */
@Data @Accessors(chain = true)
public class TopForm {

    //---------------------------------------------- properies [private].
    /** メッセージ区分. */
    private String messageKbn;

}
