package executor;

import handlers.TResultHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by serg on 22.11.14.
 */

public class PreparedTExecutor {
    public <T> T execQuery(Connection connection,
                           String query, ArrayList params,
                           TResultHandler<T> handler)
            throws SQLException {
        T value;
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try(ResultSet result = stmt.executeQuery()) {
                value = handler.handle(result);
            }
        }
        return value;
    }

    public void execUpdate(Connection connection, String update,
                           ArrayList params)
                throws SQLException {
            PreparedStatement stmt = connection.prepareStatement(update);
            for(int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            stmt.executeUpdate();
            stmt.close();
    }
}
