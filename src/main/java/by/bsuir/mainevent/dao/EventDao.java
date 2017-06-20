package by.bsuir.mainevent.dao;

import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.Location;

/**
 * Created by ulza1116 on 6/19/2017.
 */
public interface EventDao {
    void insertEvent(Event event);

    void insertEventLocation(Location location);
}
