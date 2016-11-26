package pl.themolka.commons.storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class StorageThread extends Thread {
    public static final int QUEUE_SIZE = 1024;

    private final BlockingQueue<Query> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

    public StorageThread() {
        super("Storage Thread");
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                this.queue.take().handle();
            }
        } catch (Throwable ignored) {
        }
    }

    public boolean addQuery(Query query) {
        try {
            return this.queue.add(query);
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    public BlockingQueue<Query> getQueue() {
        return this.queue;
    }
}
