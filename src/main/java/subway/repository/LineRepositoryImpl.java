package subway.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import subway.dao.InterStationDao;
import subway.dao.LineDao;
import subway.domain.InterStation;
import subway.domain.Line;
import subway.domain.Station;
import subway.entity.InterStationEntity;
import subway.entity.LineEntity;
import subway.entity.StationEntity;
import subway.exception.BusinessException;

@RequiredArgsConstructor
@Repository
public class LineRepositoryImpl implements LineRepository {

    private final LineDao lineDao;
    private final InterStationDao interStationDao;

    @Override
    public Line save(final Line line) {
        final LineEntity lineEntity = lineDao.insert(LineEntity.from(line));
        final List<InterStation> interStations = lineEntity.getInterStationEntities()
            .stream()
            .map(this::makeInterStation)
            .collect(Collectors.toList());
        return new Line(lineEntity.getId(), lineEntity.getName(), lineEntity.getColor(), interStations);
    }

    private InterStation makeInterStation(final InterStationEntity interStationEntity) {
        return new InterStation(interStationEntity.getId(),
            makeStation(interStationEntity.getFrontStation()),
            makeStation(interStationEntity.getBackStation()), interStationEntity.getDistance());
    }

    private Station makeStation(final StationEntity stationEntity) {
        return new Station(stationEntity.getId(), stationEntity.getName());
    }

    @Override
    public List<Line> findAll() {
        return lineDao.findAll()
            .stream()
            .map(this::makeLine)
            .collect(Collectors.toList());
    }

    private Line makeLine(final LineEntity lineEntity) {
        final List<InterStation> interStations = lineEntity.getInterStationEntities()
            .stream()
            .map(this::makeInterStation)
            .collect(Collectors.toList());
        return new Line(lineEntity.getId(), lineEntity.getName(), lineEntity.getColor(), interStations);
    }

    @Override
    public Line update(final Line line) {
        interStationDao.deleteAllByLineId(line.getId());
        final List<InterStationEntity> interStationEntities = line.getInterStations()
            .stream()
            .map(InterStationEntity::from)
            .collect(Collectors.toUnmodifiableList());
        interStationDao.insertAll(interStationEntities);
        return findByName(line.getName())
            .orElseThrow(BusinessException::new);
    }

    @Override
    public Optional<Line> findByName(final String name) {
        return lineDao.findByName(name)
            .map(this::makeLine);
    }

    @Override
    public void delete(final Line line) {
        lineDao.deleteById(line.getId());
    }
}
