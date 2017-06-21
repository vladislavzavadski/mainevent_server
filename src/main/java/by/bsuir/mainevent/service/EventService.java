package by.bsuir.mainevent.service;

import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.EventRole;
import by.bsuir.mainevent.domain.Subscription;

import java.text.ParseException;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ulza1116 on 6/20/2017.
 */
public interface EventService {
    void createEvent(Event event, TimeZone timeZone) throws ParseException;

    Event getEvent(int id, TimeZone timeZone) throws ParseException;

    void subscribeOnEvent(Subscription subscription);

    List<Event> getUsersEvents(int startFrom, int limit);
}
