package by.bsuir.mainevent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@Data
public class Location {
    @JsonIgnore
    private int id;
    private double latitude;
    private double longitude;
}
