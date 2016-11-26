package pl.themolka.commons.storage;

import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Storages {
    private final StorageFileManagement fileManagement = new StorageFileManagement();
    private final Map<String, Storage> storageMap = new HashMap<>();

    public StorageFileManagement getFileManagement() {
        return this.fileManagement;
    }

    public Storage getStorage(String name) {
        return this.storageMap.get(name);
    }

    public Set<String> getStorageNames() {
        return this.storageMap.keySet();
    }

    public Collection<Storage> getStorages() {
        return this.storageMap.values();
    }

    // storage management
    public boolean insertStorage(Storage storage) {
        if (!this.storageMap.containsKey(storage.getName())) {
            StorageCreateEvent event = new StorageCreateEvent(storage);
            event.post();

            this.storageMap.put(storage.getName(), storage);
            return true;
        }

        return false;
    }

    public void insertStorages(List<Storage> storageList) {
        for (Storage storage : storageList) {
            this.insertStorage(storage);
        }
    }

    public void insertStoragesFromDefaultFile() throws IOException, JDOMException {
        this.insertStorages(this.getFileManagement().readStorageFile());
    }

    public void insertStoragesFromFile(File file) throws IOException, JDOMException {
        this.insertStorages(this.getFileManagement().readStorageFile(file));
    }

    public void removeStorage(Storage storage) {
        StorageDestroyEvent event = new StorageDestroyEvent(storage);
        event.post();

        this.storageMap.remove(storage.getName());
    }
}
