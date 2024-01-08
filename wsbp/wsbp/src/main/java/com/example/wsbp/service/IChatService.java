package com.example.wsbp.service;

import com.example.wsbp.data.Chat;
import java.util.List;

public interface IChatService {

    public void registerUser(String userName, String msgBody);

    /**
     * ユーザ名とパスワードの一覧を、AuthUser型のリストで検索する
     *
     * @return AuthUser型のListインスタンス
     */
    public List<Chat> findChats();

}
