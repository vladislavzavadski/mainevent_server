package by.bsuir.mainevent.service.impl;

import by.bsuir.mainevent.dao.EventDao;
import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.EventRole;
import by.bsuir.mainevent.domain.Subscription;
import by.bsuir.mainevent.domain.User;
import by.bsuir.mainevent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by ulza1116 on 6/20/2017.
 */
@Service
public class DefaultEventService implements EventService {

    private static final String DATE_FORMAT = "dd MMM yyyy HH:mm:ss";

    private static final String GMT = "GMT";

    private final EventDao eventDao;

    @Autowired
    public DefaultEventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    @Transactional
    public void createEvent(Event event, TimeZone timeZone) throws ParseException {

        event.setCreationDate(convertDateToGreenwich(new Date(), timeZone, TimeZone.getTimeZone(GMT)));
        event.setEventDate(convertDateToGreenwich(event.getEventDate(), timeZone, TimeZone.getTimeZone(GMT)));

            User user = new User();

             user.setUsername("vladikxd4");

        event.setCreator(user);
        eventDao.insertEventLocation(event.getLocation());
        eventDao.insertEvent(event);

        Subscription subscription = new Subscription(event.getCreator(), event, EventRole.ORGANIZER);

        eventDao.subscribeOnEvent(subscription);

    }

    @Override
    public Event getEvent(int id, TimeZone timeZone) throws ParseException {
        Event event = eventDao.selectEvent(id);

        event.setCreationDate(convertDateToGreenwich(event.getCreationDate(), TimeZone.getTimeZone(GMT), timeZone));
        event.setEventDate(convertDateToGreenwich(event.getEventDate(), TimeZone.getTimeZone(GMT), timeZone));

        return event;
    }

    @Override
    public void subscribeOnEvent(Subscription subscription){
        eventDao.subscribeOnEvent(subscription);
    }

    @Override
    public List<Event> getUsersEvents(int startFrom, int limit){

        return eventDao.getUsersEvents("vladikxd4", startFrom, limit);

    }

    private Date convertDateToGreenwich(Date date, TimeZone sourceTimeZone, TimeZone destinationTimeZone) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(destinationTimeZone);

        String gmtTimeString = dateFormat.format(date);

        dateFormat.setTimeZone(sourceTimeZone);

        return dateFormat.parse(gmtTimeString);

    }

}
