package by.bsuir.mainevent.service;

import by.bsuir.mainevent.domain.Event;

import java.text.ParseException;
import java.util.TimeZone;

/**
 * Created by ulza1116 on 6/20/2017.
 */
public interface EventService {
    void createEvent(Event event, TimeZone timeZone) throws ParseException;
}
