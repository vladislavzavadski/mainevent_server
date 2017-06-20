package by.bsuir.mainevent.controller;

import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ulza1116 on 6/20/2017.
 */
@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public void createEvent(@RequestBody Event event, TimeZone timeZone) throws ParseException {

        eventService.createEvent(event, timeZone);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Event getEvent(){
        Event event = new Event();

        event.setEventDate(new Date());

        return event;
    }
}
