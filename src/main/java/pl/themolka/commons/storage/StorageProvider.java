package pl.themolka.commons.storage;

import java.util.Properties;

public enum StorageProvider {
    CUSTOM {
        @Override
        public Storage createStorage(String name, Properties data) {
            return new CustomStorage(name, data);
        }
    },

    SQLITE {
        @Override
        public Storage createStorage(String name, Properties data) {
            return new SQLiteStorage(name, data);
        }
    },
    ;

    public abstract Storage createStorage(String name, Properties data);

    public static StorageProvider find(String query) {
        for (StorageProvider provider : values()) {
            if (provider.name().equalsIgnoreCase(query)) {
                return provider;
            }
        }

        return null;
    }
}
