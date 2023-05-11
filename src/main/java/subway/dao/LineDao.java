package subway.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.entity.LineEntity;

@Repository
public class LineDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert lineInsertAction;
    private final SimpleJdbcInsert stationInsertAction;
    private final SimpleJdbcInsert InterStationInsertAction;

    public LineDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        lineInsertAction = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("line")
            .usingColumns("name", "color")
            .usingGeneratedKeyColumns("id");
        stationInsertAction = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("station")
            .usingColumns("name")
            .usingGeneratedKeyColumns("id");
        InterStationInsertAction = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("interstation")
            .usingColumns("line_id", "start_station_id", "end_station_id")
            .usingGeneratedKeyColumns("id");
    }

    public LineEntity insert(final LineEntity lineEntity) {
        final Map<String, Object> lineParams = new HashMap<>();
        lineParams.put("name", lineEntity.getName());
        lineParams.put("color", lineEntity.getColor());
        final Long lineId = lineInsertAction.executeAndReturnKey(lineParams).longValue();

        final Map<String, Object>[] interStationParams = lineEntity.getInterStationEntities()
            .stream()
            .map(interStationEntity -> {
                final Map<String, Object> stationParam = new HashMap<>();
                stationParam.put("line_id", lineId);
                stationParam.put("start_station_id", interStationEntity.getFrontStation().getId());
                stationParam.put("end_station_id", interStationEntity.getBackStation().getId());
                return stationParam;
            }).toArray(Map[]::new);
        stationInsertAction.executeBatch(interStationParams);

        return findById(lineId);
    }

    public List<LineEntity> findAll() {
        final String sql = "SELECT line.id                       AS line_id, "
            + "       line.name                     AS line_name, "
            + "       line.color                    AS line_color, "
            + "       start_station.id              AS start_station_id, "
            + "       start_station.name            AS start_station_name, "
            + "       end_station.id                AS end_station_id, "
            + "       end_station.name              AS end_station_name, "
            + "       interstation.id               AS interstation_id, "
            + "       interstation.line_id          AS interstation_line_id, "
            + "       interstation.start_station_id AS interstation_start_station_id, "
            + "       interstation.end_station_id   AS interstation_end_station_id "
            + "FROM INTERSTATION AS interstation "
            + "         JOIN LINE AS line ON line.id = interstation.line_id "
            + "         JOIN STATION AS start_station ON start_station.id = interstation.start_station_id "
            + "         JOIN STATION AS end_station ON end_station.id = interstation.end_station_id;";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public LineEntity findById(final Long id) {
        final String sql = "select id, name, color from LINE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void update(final LineEntity newLineEntity) {
        final String sql = "update LINE set name = ?, color = ? where id = ?";
        jdbcTemplate.update(sql,
            newLineEntity.getName(), newLineEntity.getColor(), newLineEntity.getId());
    }

    public void deleteById(final Long id) {
        jdbcTemplate.update("delete from Line where id = ?", id);
    }

    public Optional<LineEntity> findByName(final String name) {
        return null;
    }
}
