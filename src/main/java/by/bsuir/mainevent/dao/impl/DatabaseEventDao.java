package by.bsuir.mainevent.dao.impl;

import by.bsuir.mainevent.dao.EventDao;
import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@Repository
public class DatabaseEventDao implements EventDao {

    private static final String INSERT_EVENT_QUERY = "INSERT INTO event (ev_name, ev_creator, ev_time, ev_description, ev_max_subscribers, ev_location, ev_create_time) VALUES (:name, :creator.username, :eventDate, :description, :maxSubscribersNumber, :location.id, NOW());";
    private static final String INSERT_LOCATION_QUERY = "insert into event_location (el_latitude, el_longitude) values (?, ?);";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseEventDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void insertEvent(Event event){
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(event);

        namedParameterJdbcTemplate.update(INSERT_EVENT_QUERY, beanPropertySqlParameterSource);
    }

    @Override
    public void insertEventLocation(final Location location){

        KeyHolder keyHolder =  new GeneratedKeyHolder();

        namedParameterJdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCATION_QUERY, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setDouble(1, location.getLatitude());
                preparedStatement.setDouble(2, location.getLongitude());

                return preparedStatement;
            }
        }, keyHolder);

        location.setId(keyHolder.getKey().intValue());
    }

}
