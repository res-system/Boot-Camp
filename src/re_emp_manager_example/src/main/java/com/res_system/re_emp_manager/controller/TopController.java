package com.res_system.re_emp_manager.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.NonAuth;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.model.top.TopForm;
import com.res_system.re_emp_manager.model.top.TopModel;

/**
 * TOP画面 コントローラークラス.
 * @author res.
 */
@Path("/")
@Controller
@RequestScoped
public class TopController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE_INDEX = "top/index";
    private static final String TEMPLATE_ERROR = "top/error";



    //---------------------------------------------- [private] モデルクラス.
    @Inject
    private TopModel model;
    @Inject
    private MessageModel msg;



    //---------------------------------------------- [public] アクション(@GET).
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return new HtmlResponse().Redirect("/login");
    }

    /** メッセージ表示. */
    @Path("/show_message/{kbn}")
    @GET
    public HtmlResponse showMessage(@PathParam("kbn") final String messageKbn) throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        form.setMessageKbn(messageKbn);
        model.showMessage(form);
        return new HtmlResponse(TEMPLATE_ERROR).add("form", form)
                .add("messageList", model.getMessageList());
    }



    //---------------------------------------------- [public] テストアクション(@GET).
    /** テスト画面表示. */
    @Path("/test")
    @GET
    public HtmlResponse index() throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        return new HtmlResponse(TEMPLATE_INDEX).add("form", form);
    }

    /** テストアクション(@POST). */
    @Path("/test_post")
    @POST
    public HtmlResponse doPost(MultivaluedMap<String, String> params) throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        List<Message> messageList = new ArrayList<>();
        messageList.add(msg.getMessage("I00001", "「テストアクション(@POST)」が実行されました。ー" + params.get("link_test_form_txt1")));
        return new HtmlResponse(TEMPLATE_INDEX)
                .add("form", form)
                .add("messageList", messageList);
    }

    /** テストアクション(@POST[JSON]). */
    @Path("/test_action")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doDelete(MultivaluedMap<String, String> params) throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        List<Message> messageList = new ArrayList<>();
        messageList.add(msg.getMessage("I00001", "「テストアクション(@POST[JSON])」が実行されました。ー" + params.get("link_test_form_txt1")));
        String status = AjaxResponse.OK;
        return new AjaxResponse(status).setForm(form).setMessageList(messageList);
    }

    /** テストアクション(@POST[UPLOAD]). */
    @Path("/test_upload")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpload(
              @FormDataParam("file") InputStream file
            , @FormDataParam("file") FormDataContentDisposition dispoosition
            , @FormDataParam("type") String type) throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        List<Message> messageList = new ArrayList<>();
        messageList.add(msg.getMessage("I00001", "「テストアクション(@POST[UPLOAD])」が実行されました。ー" + dispoosition.getFileName()));
        String status = AjaxResponse.OK;
        return new AjaxResponse(status).setForm(form).setMessageList(messageList);
    }

    /** テストアクション(@POST[DOWNLOAD]). */
    @Path("/test_download/{key}")
    @GET
    @Produces("application/pdf")
    @NonAuth
    public Response getGif(@PathParam("key") String key) throws Exception {
        ResponseBuilder response = Response.ok((Object)key);
        String headerVal = "attachment; filename=" + key + ".pdf";
        response.header("Content-Disposition", headerVal);
        return response.build();
    }

}
