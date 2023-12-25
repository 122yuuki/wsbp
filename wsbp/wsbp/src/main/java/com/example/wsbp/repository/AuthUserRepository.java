package com.example.wsbp.repository;

import com.example.wsbp.data.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.util.List;

@Repository
public class AuthUserRepository implements IAuthUserRepository{
    private final JdbcTemplate jdbc;

    @Autowired
    public AuthUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int insert(String userName, String userPass) {
        var sql = "insert into auth_user values (?, ?)";
        var n = jdbc.update(sql, userName, userPass);
        return n;
    }

    @Override
    public int delete(String userName) {
        var sql = "delete from auth_user where user_name = ?";
        var n = jdbc.update(sql, userName);
        return n;
    }
    @Override
    public boolean exists(String userName, String userPass) {

        var sql = "select true from auth_user "
                + "where user_name = ? and user_pass = ?";

        var booles = jdbc.query(sql,
                SingleColumnRowMapper.newInstance(Boolean.class),
                userName, userPass);

        return !booles.isEmpty();
    }

    @Override
    public List<AuthUser> find() {
        // auth_user テーブルの user_name, user_pass を検索する
        String sql = "select user_name, user_pass from auth_user";

        // 検索用のSQLを実行する方法。
        // 取り出したいデータの型によって、第2引数の RowMapper を切り替える。
        // ? を使うSQLであれば、第3引数の Object型配列 の要素に順番に設定する。
        List<AuthUser> users = jdbc.query(sql,
                DataClassRowMapper.newInstance(AuthUser.class));

        // 取り出したデータ（Listの要素）をそのまま返値とする。
        return users;
    }

}
