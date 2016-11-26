package pl.themolka.commons.storage;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Storage {
    private final String name;
    private final StorageThread thread = new StorageThread();

    public Storage() {
        this(RandomStringUtils.random(10));
    }

    public Storage(String name) {
        this.name = name;
    }

    public abstract void connect() throws SQLException;

    public void disconnect() throws SQLException {
        this.getConnection().close();
    }

    public abstract Connection getConnection() throws SQLException;

    public String getDbName() {
        return "Unknown";
    }

    public abstract String getDriver();

    public String getName() {
        if (this.name != null) {
            return this.name;
        } else {
            return this.getDbName();
        }
    }

    public StorageThread getThread() {
        return this.thread;
    }

    // creating new Query objects
    public Query query() {
        return Query.newQuery().storage(this);
    }

    public Query query(String query) {
        return Query.newQuery(query).storage(this);
    }

    public Query query(String query, Object... params) {
        return Query.newQuery(query, params).storage(this);
    }
}
