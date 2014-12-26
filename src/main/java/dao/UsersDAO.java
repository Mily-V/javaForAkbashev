package dao;

import dataSets.UserDataSet;
import executor.PreparedTExecutor;
import handlers.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by serg on 21.11.14.
 */
public class UsersDAO {

    private Connection con;
    PreparedTExecutor exec = new PreparedTExecutor();

    public UsersDAO(Connection con){
        this.con = con;
    }

    public UserDataSet put(UserDataSet user) throws SQLException {
        ArrayList <Object> param = new ArrayList<>();
        param.add(user.getLogin());
        param.add(user.getPassword());
        param.add(user.getEmail());
        String update = "INSERT into users(user_login, user_password, user_email) values(?,?,?)";
        exec.execUpdate(con, update, param);
        ArrayList param2 = new ArrayList<>();
        long id = exec.execQuery(con, "SELECT LAST_INSERT_ID()", param2, new TResultHandler<Long>(){

            public Long handle(ResultSet result) throws SQLException {
                result.next();
                return result.getLong(1);
            }

        });
        user = new UserDataSet(id, (String)param.get(0), (String)param.get(1), (String)param.get(2), 0);
        return user;
    }

    public UserDataSet get(String login) throws SQLException {
        ArrayList <String> param = new ArrayList<>();
        param.add(login);
        return exec.execQuery(con, "select * from users where user_login = ?", param, new TResultHandler<UserDataSet>(){

            public UserDataSet handle(ResultSet result) throws SQLException {
                if (result.next()) {
                    return new UserDataSet(result.getLong("user_id"), result.getString("user_login"),
                            result.getString("user_password"), result.getString("user_email"), result.getLong("score"));
                } else return null;
            }

        });
    }

    public long count () throws SQLException {
        ArrayList <String> param = new ArrayList<>();
        return exec.execQuery(con, "select count(*) from users", param, new TResultHandler<Long>(){

            public Long handle(ResultSet result) throws SQLException {
                result.next();
                return result.getLong(1);
            }

        });
    }

    public boolean contains (String login) throws SQLException {
        ArrayList <String> param = new ArrayList<>();
        param.add(login);
        return(1 == exec.execQuery(con, "select count(*) from users where user_login = ?", param, new TResultHandler<Long>(){

            public Long handle(ResultSet result) throws SQLException {
                result.next();
                return result.getLong(1);
            }

        }));
    }

    public void truncate() throws SQLException {
        ArrayList <Object> param = new ArrayList<>();
        String update = "TRUNCATE TABLE users";
        exec.execUpdate(con, update, param);
    }
}
