package com.parking.parkinglot.ejb;
import java.util.logging.Logger;
import java.util.List;
import com.parking.parkinglot.common.CarDto;
import jakarta.ejb.EJBException;
import jakarta.persistence.EntityManager;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import com.parking.parkinglot.entities.Car;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;

@Stateless

public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("findAllCars");
        try{
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        }
        catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtoList = new ArrayList<>();

        for (Car car : cars) {
            CarDto dto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            carDtoList.add(dto);
        }

        return carDtoList;
    }

}
