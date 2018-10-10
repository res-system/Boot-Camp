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
import com.res_system.re_emp_manager.commons.controller.interceptor.EmpConvCtrl;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.commons.kind.Sex;
import com.res_system.re_emp_manager.commons.kind.Sitch;
import com.res_system.re_emp_manager.model.emp_info.EmpInfoForm;
import com.res_system.re_emp_manager.model.emp_info.EmpInfoModel;

/**
 * 社員個人情報管理画面 コントローラークラス.
 * @author res.
 */
@Path("/emp_info")
@Controller
@LoginAuth
@Permission
@EmpConvCtrl
@RequestScoped
public class EmpInfoController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [show] */
    private static final String TEMPLATE_SHOW = "emp_info/show";
    /** VIEWファイル [input] */
    private static final String TEMPLATE_INPUT = "emp_info/input";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmpInfoModel model;



    //---------------------------------------------- [public] アクション(@GET).
    /** 初期表示. */
    @Path("/init")
    @GET
    public HtmlResponse doInit() throws Exception {
        EmpInfoForm form = model.init(FormUtil.make(EmpInfoForm.class));
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form);
    }

    /** 画面表示. */
    @Path("/show")
    @GET
    public HtmlResponse doShow() throws Exception {
        EmpInfoForm form = model.initShow(FormUtil.make(EmpInfoForm.class));
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form);
   }

    /** 入力画面表示. */
    @Path("/input")
    @GET
    public HtmlResponse doInput() throws Exception {
        EmpInfoForm form = model.initInput(FormUtil.make(EmpInfoForm.class));
        return new HtmlResponse(TEMPLATE_INPUT).add("form", form)
                .add("SitchList", KindUtil.toItemList(Sitch.values()))
                .add("SexList", KindUtil.toItemList(Sex.values()))
                ;
   }

    /** 更新完了表示. */
    @Path("/complete")
    @GET
    public HtmlResponse doComplete() throws Exception {
        EmpInfoForm form = model.initComplete(FormUtil.make(EmpInfoForm.class));
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form)
                .add("messageList", model.getMessageList())
                ;
   }



    //---------------------------------------------- [public] アクション(@POST).
    /** 選択処理. */
    @Path("/next")
    @POST
    public HtmlResponse doNext(MultivaluedMap<String, String> params) throws Exception {
        EmpInfoForm form = model.initNext(FormUtil.make(EmpInfoForm.class, params));
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form);
   }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        EmpInfoForm form = FormUtil.make(EmpInfoForm.class, params);
        String status;
        if (model.checkInput(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 更新. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        EmpInfoForm form = FormUtil.make(EmpInfoForm.class, params);
        String status;
        if (model.checkInput(form) && model.updateData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }


    /** 現住所取得. */
    @Path("/get_current_addr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doGetCurrentAddr(MultivaluedMap<String, String> params) throws Exception {
        EmpInfoForm form = FormUtil.make(EmpInfoForm.class, params);
        String status;
        if (model.getCurrentAddr(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
