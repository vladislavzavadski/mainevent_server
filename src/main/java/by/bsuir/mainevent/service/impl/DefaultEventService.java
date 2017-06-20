package by.bsuir.mainevent.service.impl;

import by.bsuir.mainevent.dao.EventDao;
import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.User;
import by.bsuir.mainevent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * Created by ulza1116 on 6/20/2017.
 */
@Service
public class DefaultEventService implements EventService {

    private static final String DATE_FORMAT = "dd MMM yyyy HH:mm:ss zzz";

    private static final String GMT = "GMT";

    private final EventDao eventDao;

    @Autowired
    public DefaultEventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    @Transactional
    public void createEvent(Event event, TimeZone timeZone) throws ParseException {

        Calendar calendar = new GregorianCalendar(timeZone);

        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        formatter.setTimeZone(TimeZone.getTimeZone(GMT));


        String gmtTimeString = formatter.format(calendar.getTime());

        event.setCreationDate(formatter.parse(gmtTimeString));

        event.setEventDate(formatter.parse(formatter.format(event.getEventDate())));

             User user = new User();

             user.setUsername("vladikxd4");

        event.setCreator(user);
        eventDao.insertEventLocation(event.getLocation());
        eventDao.insertEvent(event);

    }

}
