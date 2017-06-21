package by.bsuir.mainevent.controller;

import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.Subscription;
import by.bsuir.mainevent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
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

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable("id") int eventId, TimeZone timeZone) throws ParseException {

        return eventService.getEvent(eventId, timeZone);
    }

    @RequestMapping(value = "/event/subscribe", method = RequestMethod.POST)
    public void subscribeOnEvent(@RequestBody Subscription subscription){
        eventService.subscribeOnEvent(subscription);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(value = "startFrom") int startFrom, @RequestParam(value = "limit") int limit){
        return eventService.getUsersEvents(startFrom, limit);
    }

}
