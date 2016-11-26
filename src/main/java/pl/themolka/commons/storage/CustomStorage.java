package pl.themolka.commons.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class CustomStorage extends Storage {
    private final HikariConfig configuration;
    private HikariDataSource dataSource;

    public CustomStorage(String name, Properties data) {
        this(name, new HikariConfig(data));
    }

    public CustomStorage(String name, HikariConfig configuration) {
        super(name);

        this.configuration = configuration;
    }

    @Override
    public void connect() throws SQLException {
        this.dataSource = new HikariDataSource(this.getConfiguration());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getDataSource().getConnection();
    }

    @Override
    public String getDbName() {
        return this.getDriver();
    }

    @Override
    public String getDriver() {
        return this.getDataSource().getDriverClassName();
    }

    public HikariConfig getConfiguration() {
        return this.configuration;
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }
}
