package by.bsuir.mainevent.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ulza1116 on 6/21/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private User user;
    private Event event;
    private EventRole eventRole;
}
