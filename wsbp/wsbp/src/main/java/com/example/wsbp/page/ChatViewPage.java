package com.example.wsbp.page;

import com.example.wsbp.data.Chat;
import com.example.wsbp.page.signed.SignedPage;
import com.example.wsbp.service.IChatService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("ChatView")
public class ChatViewPage extends WebPage {
    @SpringBean
    private IChatService chatService;

    public ChatViewPage(){
        var chatsModel = Model.ofList(chatService.findChats());
        var usersLV = new ListView<>("chats", chatsModel) {
            @Override
            protected void populateItem(ListItem<Chat> listItem) {

                var itemModel = listItem.getModel();
                var chat = itemModel.getObject();

                var userNameModel = Model.of(chat.getUserName());
                var userNameLabel = new Label("userName", userNameModel);
                listItem.add(userNameLabel);

                var msgBodyModel = Model.of(chat.getMsgBody());
                var msgBodyLabel = new Label("msgBody", msgBodyModel);
                listItem.add(msgBodyLabel);
            }
        };
        add(usersLV);

        var toHomeLink = new BookmarkablePageLink<>("toHome", SignedPage.class);
        add(toHomeLink);
    }
}
