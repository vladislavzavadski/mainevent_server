package by.bsuir.mainevent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@Data
public class Event {

    private int id;
    private String name;
    private String description;
    private Location location;
    private Date eventDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date creationDate;

    private User creator;
    private int maxSubscribersNumber;
    private Map<EventRole, List<User>> user;

}
