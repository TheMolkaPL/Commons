package pl.themolka.commons.storage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLiteStorage extends Storage {
    public static final String FILE_EXTENSION = ".db";
    public static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    public static final String SQLITE_NAME = "SQLite";

    private Connection connection;
    private final File file;

    public SQLiteStorage(String name, Properties data) {
        this(name, new File(data.getOrDefault("file", "sqlite" + FILE_EXTENSION).toString()));
    }

    public SQLiteStorage(String name, File file) {
        super(name);

        this.file = file;
    }

    @Override
    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.getDbFile().getPath());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connection;
    }

    @Override
    public String getDbName() {
        return SQLITE_NAME;
    }

    @Override
    public String getDriver() {
        return SQLITE_DRIVER;
    }

    public File getDbFile() {
        return this.file;
    }
}
