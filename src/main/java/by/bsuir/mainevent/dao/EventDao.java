package by.bsuir.mainevent.dao;

import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.Location;
import by.bsuir.mainevent.domain.Subscription;

import java.util.List;

/**
 * Created by ulza1116 on 6/19/2017.
 */
public interface EventDao {
    void insertEvent(Event event);

    void insertEventLocation(Location location);

    Event selectEvent(int id);

    void subscribeOnEvent(Subscription subscription);

    List<Event> getUsersEvents(String username, int startFrom, int limit);
}
