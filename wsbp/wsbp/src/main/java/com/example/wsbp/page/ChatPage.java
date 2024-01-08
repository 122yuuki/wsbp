package com.example.wsbp.page;

import com.example.wsbp.MySession;
import com.example.wsbp.page.signed.SignedPage;
import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.example.wsbp.service.IChatService;

@MountPath("ChatIn")
public class ChatPage extends WebPage {
    @SpringBean
    private IChatService chatService;

    public ChatPage(){
        var userNameModel = Model.of("");
        var msgBodyModel = Model.of("");

        var chatInfoForm = new Form<>("chatInfo"){
            @Override
            protected void onSubmit() {
                var userName = userNameModel.getObject();
                var msgBody = msgBodyModel.getObject();
                var msg = "送信データ："
                        + userName
                        + ","
                        + msgBody;
                System.out.println(msg);
                chatService.registerUser(userName,msgBody);
                setResponsePage(new ChatCompPage(userNameModel));
            }
        };
        add(chatInfoForm);

        var userNameField = new TextField<>("userName",userNameModel){
            @Override
            protected void onInitialize() {
                super.onInitialize();
                // 文字列の長さを8〜32文字に制限するバリデータ
                var validator = StringValidator.lengthBetween(8, 32);
                add(validator);
            }
        };
        chatInfoForm.add(userNameField);

        var msgBodyField = new TextField<>("msgBody",msgBodyModel){
            @Override
            protected void onInitialize() {
                super.onInitialize();
                // 文字列の長さを8〜32文字に制限するバリデータ
                var validator = StringValidator.lengthBetween(1, 200);
                add(validator);
            }
        };
        chatInfoForm.add(msgBodyField);

    }


}
