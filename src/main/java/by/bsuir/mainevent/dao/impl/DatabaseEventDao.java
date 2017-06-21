package by.bsuir.mainevent.dao.impl;

import by.bsuir.mainevent.dao.EventDao;
import by.bsuir.mainevent.dao.TableColumn;
import by.bsuir.mainevent.domain.Event;
import by.bsuir.mainevent.domain.EventType;
import by.bsuir.mainevent.domain.Location;
import by.bsuir.mainevent.domain.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@Repository
public class DatabaseEventDao implements EventDao {

    private static final String INSERT_EVENT_QUERY = "INSERT INTO event (ev_name, ev_creator, ev_time, ev_description, ev_max_subscribers, ev_location, ev_create_time, ev_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_LOCATION_QUERY = "insert into event_location (el_latitude, el_longitude) values (?, ?);";

    private static final String SELECT_EVENT_QUERY = "SELECT ev_id, ev_name, ev_time, ev_description, ev_max_subscribers, ev_create_time, el_latitude, el_longitude from event INNER JOIN event_location on el_id = ev_location where ev_id = ?;";
    private static final String SUBSCRIBE_USER_ON_EVENT_QUERY = "INSERT INTO user_event (ue_username, ue_event_id, ue_role) VALUES (?, ?, ?)";

    private static final String SELECT_USERS_SUBSCRIPTIONS = "select ev_id, ev_name, ev_type, et_name from event inner JOIN type on ev_type=type.et_id inner join user_event on ue_event_id=ev_id where ue_username = ? limit ?, ?;";

    private static final RowMapper<Event> userEventsRowMapper = new RowMapper<Event>() {

        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {

            Event event = new Event();

            event.setId(resultSet.getInt(TableColumn.EVENT_ID));
            event.setName(resultSet.getString(TableColumn.EVENT_NAME));

            EventType eventType = new EventType();

            eventType.setId(resultSet.getInt(TableColumn.EVENT_TYPE));
            eventType.setName(resultSet.getString(TableColumn.EVENT_TYPE_NAME));

            event.setEventType(eventType);

            return event;
        }

    };

    private static final RowMapper<Event> eventRowMapper = new RowMapper<Event>() {

        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new Event();

            event.setName(resultSet.getString(TableColumn.EVENT_NAME));
            event.setEventDate(resultSet.getTimestamp(TableColumn.EVENT_TIME));
            event.setDescription(resultSet.getString(TableColumn.EVENT_DESCRIPTION));
            event.setMaxSubscribersNumber(resultSet.getInt(TableColumn.EVENT_MAX_SUBSCRIBERS));
            event.setCreationDate(resultSet.getTimestamp(TableColumn.EVENT_CREATE_TIME));
            event.setId(resultSet.getInt(TableColumn.EVENT_ID));
            Location location = new Location();

            location.setLatitude(resultSet.getDouble(TableColumn.LOCATION_LATITUDE));
            location.setLongitude(resultSet.getDouble(TableColumn.LOCATION_LONGITUDE));

            event.setLocation(location);

            return event;
        }
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseEventDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void insertEvent(final Event event){

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, event.getName());
                preparedStatement.setString(2, event.getCreator().getUsername());
                preparedStatement.setDate(3, new Date(event.getEventDate().getTime()));
                preparedStatement.setString(4, event.getDescription());
                preparedStatement.setInt(5, event.getMaxSubscribersNumber());
                preparedStatement.setInt(6, event.getLocation().getId());
                preparedStatement.setDate(7, new Date(event.getCreationDate().getTime()));
                preparedStatement.setInt(8, event.getEventType().getId());

                return preparedStatement;
            }
        }, keyHolder);

        event.setId(keyHolder.getKey().intValue());
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

    @Override
    public Event selectEvent(int id){
        return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SELECT_EVENT_QUERY, eventRowMapper, id);
    }

    @Override
    public void subscribeOnEvent(Subscription subscription){

        namedParameterJdbcTemplate.getJdbcOperations().update(SUBSCRIBE_USER_ON_EVENT_QUERY,
                subscription.getUser().getUsername(),
                subscription.getEvent().getId(), subscription.getEventRole().toString());
    }

    @Override
    public List<Event> getUsersEvents(String username, int startFrom, int limit){
        return namedParameterJdbcTemplate.getJdbcOperations().query(SELECT_USERS_SUBSCRIPTIONS,
                new Object[]{username, limit, startFrom}, userEventsRowMapper);
    }

}
