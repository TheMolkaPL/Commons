package pl.themolka.commons.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query {
    private final List<QueryCallback> forEachCalls = new ArrayList<>();
    private boolean handling;
    private Object[] params = new Object[0];
    private final List<QueryCallback> resultCalls = new ArrayList<>();
    private boolean select;
    private Storage storage;
    private String query;

    private Query() {
    }

    public boolean detectSelect() {
        String query = this.getQuery().toUpperCase();
        return query.startsWith("SELECT");
    }

    public Query forEach(QueryCallback callback) {
        this.forEachCalls.add(callback);
        return this;
    }

    public List<QueryCallback> getForEachCalls() {
        return this.forEachCalls;
    }

    public Object[] getParams() {
        return this.params;
    }

    public List<QueryCallback> getResultCalls() {
        return this.resultCalls;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public String getQuery() {
        return this.query;
    }

    public Query handle() {
        return this.handle(this.getStorage());
    }

    public Query handle(Storage storage) {
        if (!this.isSelect()) {
            this.select(this.detectSelect());
        }

        this.handling = true;

        try (PreparedStatement statement = storage.getConnection().prepareStatement(this.getQuery())) {
            for (int i = 0; i < this.getParams().length; i++) {
                statement.setObject(i + 1, this.getParams()[i]);
            }

            ResultSet result = null;
            int count;

            if (this.isSelect()) {
                result = statement.executeQuery();
                count = result.getFetchSize();
            } else {
                count = statement.executeUpdate();
            }

            for (QueryCallback callback : this.getResultCalls()) {
                callback.onResult(result, -1, count);
            }

            if (result != null) {
                result.first();
                for (int i = 0; i < this.getForEachCalls().size(); i++) {
                    while (result.next()) {
                        QueryCallback callback = this.getForEachCalls().get(i);
                        callback.onResult(result, i, count);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        this.handling = false;
        return this;
    }

    public boolean isHandling() {
        return this.handling;
    }

    public boolean isSelect() {
        return this.select;
    }

    public Query param(int index, Object value) {
        if (this.params.length <= index) {
            this.params = Arrays.copyOf(this.params, this.params.length + 1);
        }

        this.params[index] = value;
        return this;
    }

    public Query param(Object value) {
        return this.param(this.params.length - 1, value);
    }

    public Query params(Object... params) {
        this.params = params;
        return this;
    }

    public Query removeForEachCall(QueryCallback callback) {
        this.forEachCalls.remove(callback);
        return this;
    }

    public Query removeResultCall(QueryCallback callback) {
        this.resultCalls.remove(callback);
        return this;
    }

    public Query result(QueryCallback callback) {
        this.resultCalls.add(callback);
        return this;
    }

    public Query select(boolean select) {
        this.select = select;
        return this;
    }

    public Query storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Query submit() {
        return this.submit(this.getStorage());
    }

    public Query submit(Storage storage) {
        return this.submit(storage.getThread());
    }

    public Query submit(StorageThread thread) {
        thread.addQuery(this);
        return this;
    }

    public Query query(String query) {
        this.query = query;
        return this;
    }

    // creating new Query objects
    public static Query newQuery() {
        return new Query();
    }

    public static Query newQuery(String query) {
        return new Query().query(query);
    }

    public static Query newQuery(String query, Object... params) {
        return new Query().query(query).params(params);
    }
}
