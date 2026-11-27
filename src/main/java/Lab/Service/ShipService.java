package Lab.Service;

import Lab.Exceptions.InvalidTonnageException;
import Lab.Model.Ship;
import Lab.Repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = InvalidTonnageException.class)
public class ShipService {

    ShipRepository shipRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    /**
     * Add a list of ships to the repository.
     *
     * @param ships transient ship entities
     * @throws InvalidTonnageException ships cannot have negative tonnage
     */
    public List<Ship> addListShips(List<Ship> ships)
            throws InvalidTonnageException {

        List<Ship> persistedShips = new ArrayList<>();

        for (int i = 0; i < ships.size(); i++) {

            if (ships.get(i).getTonnage() < 0) {
                throw new InvalidTonnageException();
            }

            persistedShips.add(shipRepository.save(ships.get(i)));
        }

        return persistedShips;
    }

    /**
     * @return all ship entities
     */
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    /**
     * @return ship entity by id
     */
    public Ship getShipById(long id) {
        return shipRepository.findById(id).get();
    }
}