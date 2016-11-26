package pl.themolka.commons.storage;

import java.sql.ResultSet;

public interface QueryCallback {
    void onResult(ResultSet result, int count);
}
