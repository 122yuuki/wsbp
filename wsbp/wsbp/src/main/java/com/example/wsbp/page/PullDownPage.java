package com.example.wsbp.page;

import org.wicketstuff.annotation.mount.MountPath;
import org.apache.wicket.markup.html.WebPage;
import com.example.wsbp.service.IUserService;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.model.LoadableDetachableModel;
import com.example.wsbp.data.AuthUser;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import java.util.Objects;

@MountPath("PullDown")
public class PullDownPage extends WebPage{
    @SpringBean
    private IUserService userService;

    public PullDownPage(){
        var selectionModel = LoadableDetachableModel.of(() -> userService.findAuthUsers());
        var selectedModel = new Model<AuthUser>();

        var form = new Form<>("form"){

            @Override
            protected void onSubmit() {
                // 次ページに渡すModelを用意する。
                // Wicketではさまざまな機能をもったModelを利用できるが、この機能が別ページで想定していない動きをすると動作不良の要因になる場合がある。
                // そのため、次ページにデータを渡す時には新しいModelに設定し直すことを心がけると、バグの防止になる。
                var sendingModel = new Model<>(selectedModel.getObject());
                if (Objects.isNull(sendingModel.getObject())) {
                    // 選択肢が選ばれていない場合の処理
                    // nullをそのまま渡すと、次ページでNullPointerExceptionが発生する要因になるので、ダミーのデータを渡す。
                    var dummyData = new AuthUser("ユーザー名未設定", "パスワード未設定");
                    sendingModel.setObject(dummyData);
                }
                // 次ページに渡すModelを使って、次ページ（ChoiceResultPage）を作成し移動する。
                setResponsePage(new ChoiceCompPage(sendingModel));
            }
        };
        add(form);

        var renderer = new ChoiceRenderer<>("userName");

        var userSelection = new DropDownChoice<>("userSelection", selectedModel, selectionModel, renderer){

            @Override
            protected void onInitialize(){
                super.onInitialize();
                setNullValid(true);
                setRequired(true);
                setLabel(Model.of("学籍番号の選択"));
            }

            @Override
            protected String getNullValidDisplayValue(){
                return getNullKeyDisplayValue();
            }
        };
        form.add(userSelection);

        var feedback = new FeedbackPanel("feedback");
        form.add(feedback);
    }
}
