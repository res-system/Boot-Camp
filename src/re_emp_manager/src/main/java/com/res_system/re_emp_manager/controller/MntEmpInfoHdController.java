package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.commons.mvc.model.kind.KindUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.commons.kind.InfType;
import com.res_system.re_emp_manager.commons.kind.ReqFlg;
import com.res_system.re_emp_manager.commons.kind.Stat;
import com.res_system.re_emp_manager.model.mnt_emp_info_hd.MntEmpInfoHdForm;
import com.res_system.re_emp_manager.model.mnt_emp_info_hd.MntEmpInfoHdModel;

/**
 * 社員情報ヘッダーマスタメンテナンス画面 コントローラークラス.
 * @author res.
 */
@Path("/mnt_emp_info_hd")
@Controller
@LoginAuthority
@RequestScoped
public class MntEmpInfoHdController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "mnt_emp_info_hd/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private MntEmpInfoHdModel model;



    //---------------------------------------------- [public] アクション(@GET).
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        MntEmpInfoHdForm form = model.initIndex(FormUtil.make(MntEmpInfoHdForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form)
                .add("InfTypeList", KindUtil.toItemList(InfType.values()))
                .add("ReqFlgList", KindUtil.toItemList(ReqFlg.values()))
                .add("StatList", KindUtil.toItemList(Stat.values()))
                ;
   }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** データ検索. */
    @Path("/find_data")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindData(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.findData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 最大表示順取得. */
    @Path("/get_max_seq")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doGetMaxSeq(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.getMaxSeq(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }


    /** データ件数チェック. */
    @Path("/check_size")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheckSize(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.checkSize(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.checkInput(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }


    /** 新規追加. */
    @Path("/insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doInsert(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.checkInput(form) && model.insertData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 更新. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.checkInput(form) && model.updateData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 削除. */
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doDelete(MultivaluedMap<String, String> params) throws Exception {
        MntEmpInfoHdForm form = FormUtil.make(MntEmpInfoHdForm.class, params);
        String status;
        if (model.checkInput(form) && model.deleteData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
