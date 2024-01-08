package com.example.wsbp.page.signed;

import com.example.wsbp.page.UserMakerPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.link.Link;
import com.example.wsbp.MySession;
import com.example.wsbp.page.SignPage;
import org.apache.wicket.markup.html.list.ListView;
import com.example.wsbp.data.AuthUser;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.example.wsbp.service.IUserService;
import com.example.wsbp.page.ChatPage;
import com.example.wsbp.page.ChatViewPage;

@AuthorizeInstantiation(Roles.USER)
@MountPath("Signed")
public class SignedPage extends WebPage{

    @SpringBean
    private IUserService userService;
    public SignedPage() {
        var name = Model.of(MySession.get().getUserName());
        var userNameLabel = new Label("userName", name);
        add(userNameLabel);

        Link<Void> signoutLink = new Link<Void>("signout") {
            @Override
            public void onClick() {
                // セッションの破棄
                MySession.get().invalidate();
                // SignPageへ移動
                setResponsePage(SignPage.class);
            }
        };
        add(signoutLink);

        var authUsersModel = Model.ofList(userService.findAuthUsers());
        var usersLV = new ListView<>("users", authUsersModel) {
            @Override
            protected void populateItem(ListItem<AuthUser> listItem) {

                var itemModel = listItem.getModel();
                var authUser = itemModel.getObject();

                var userNameModel = Model.of(authUser.getUserName());
                var userNameLabel = new Label("userName", userNameModel);
                listItem.add(userNameLabel);

                var userPassModel = Model.of(authUser.getUserPass());
                var userPassLabel = new Label("userPass", userPassModel);
                listItem.add(userPassLabel);
            }
        };
        add(usersLV);

        var toChatInLink = new BookmarkablePageLink<>("chatIn", ChatPage.class);
        add(toChatInLink);

        var toChatViewLink = new BookmarkablePageLink<>("chatView", ChatViewPage.class);
        add(toChatViewLink);
    }

}
