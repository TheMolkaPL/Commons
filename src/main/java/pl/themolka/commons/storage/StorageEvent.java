package pl.themolka.commons.storage;

import pl.themolka.commons.event.Event;

public class StorageEvent extends Event {
    private final Storage storage;

    public StorageEvent(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return this.storage;
    }
}
