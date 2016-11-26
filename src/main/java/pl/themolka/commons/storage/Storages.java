package pl.themolka.commons.storage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Storages {
    private IStorageFileManagement fileManagement;
    private final Map<String, Storage> storageMap = new HashMap<>();

    public Storages() {
        this.fileManagement = new XMLFileManagement(); // default file management
    }

    public IStorageFileManagement getFileManagement() {
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
        if (storageList != null) {
            for (Storage storage : storageList) {
                this.insertStorage(storage);
            }
        }
    }

    public void insertStoragesFromDefaultFile() throws IOException {
        this.insertStorages(this.getFileManagement().readDefaultFile());
    }

    public void insertStoragesFromFile(File file) throws IOException {
        this.insertStorages(this.getFileManagement().readFile(file));
    }

    public void removeStorage(Storage storage) {
        StorageDestroyEvent event = new StorageDestroyEvent(storage);
        event.post();

        this.storageMap.remove(storage.getName());
    }

    public void setFileManagement(IStorageFileManagement fileManagement) {
        this.fileManagement = fileManagement;
    }
}
