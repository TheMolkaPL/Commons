package pl.themolka.commons.event;

import com.google.common.eventbus.EventBus;

public class Events extends EventBus {
    @Override
    public void post(Object event) {
        if (event instanceof Event) {
            super.post(event);
        }
    }
}
