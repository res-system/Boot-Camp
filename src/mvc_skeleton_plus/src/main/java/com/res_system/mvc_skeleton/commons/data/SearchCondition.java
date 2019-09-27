package com.res_system.mvc_skeleton.commons.data;

import java.io.Serializable;

import com.res_system.commons.mvc.model.form.Param;
import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.dao.AppDao;
import com.res_system.mvc_skeleton.commons.kind.CheckFlg;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 検索条件 データクラス.
 * @author res.
 */
@SuppressWarnings("serial")
@Data @Accessors(chain = true)
public class SearchCondition implements Serializable {

    //---------------------------------------------- properies [private].
    /** 検索キーワード. */
    @Param
    private String keyword;
    /** 全検索チェック. */
    @Param
    private String is_all;
    /** ページ番号. */
    @Param
    private String page;
    /** 総ページ数. */
    @Param
    private String total_page;
    /** 総件数. */
    @Param
    private String total_size;
    /** 選択ID. */
    @Param
    private String selected_id;
    /** 選択Sub-ID. */
    @Param
    private String selected_sub_id;
    /** 並び順. */
    @Param
    private String sort;
 
    /** 検索キーワード(配列). */
    String[] keywords;

    /** cid(受け渡し用). */
    @Param
    private String cid;


    //-- setter / getter. --//
    /** 検索キーワード(配列) を取得します. */
    public String[] getKeywords() { 
        if (keywords == null) {
            keywords = AppDao.makeKeywords(keyword);
        }
        return keywords; 
    }



    //---------------------------------------------- [public].
    /** ページ番号 を取得します. */
    public int getPageInt() { 
        int page = ReUtil.toInt(getPage(), 1);
        setPage(String.valueOf(page));
        return page; 
    }

    /** 全検索チェック を取得します. */
    public boolean isAll() { 
        return CheckFlg.ON.equals(getIs_all()); 
    }

    /** 総件数 を取得します. */
    public Long getTotalSize() { 
        return ReUtil.toLong(total_size, 0L); 
    }

    /** リセットします. */
    public void reset() {
        setKeyword("");
        setKeywords(null);
        setIs_all(CheckFlg.OFF.getValue());
        setPage("1");
        setTotal_page("1");
        setTotal_size("0");
        setSelected_id("");
        setSelected_sub_id("");
        setSort("");
        setCid("");
    }

}
