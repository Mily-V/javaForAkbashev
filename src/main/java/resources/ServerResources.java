package resources;

import java.io.Serializable;

/**
 * Created by Dmitry on 025 25.10.14.
 */
public class ServerResources implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private int port;
    private String db_name;

    public ServerResources() {
        port = 8080;
        db_name = "FORUMS_TP";
    }

    public ServerResources(int port) {
        setPort(port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getDb_name() {
        return db_name;
    }

    public String toString() {
        return "Host: " + port + " db_name: " + db_name;
    }
}