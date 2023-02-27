import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EventManager {
     Map<String, List<Listener>> listeners = new HashMap<>();

    public EventManager(String... eventTypes) {
        for (String operation : eventTypes) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void addListener(String eventType, Listener listener) {
        List<Listener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void removeListener(String eventType, Listener listener) {
        List<Listener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, Object file) {
        List<Listener> users = listeners.get(eventType);
        for (Listener listener : users) {
            listener.update(eventType, file);
        }
    }
}
