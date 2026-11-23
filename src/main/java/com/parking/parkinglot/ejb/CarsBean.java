package com.parking.parkinglot.ejb;
import java.util.logging.Logger;
import java.util.List;
import com.parking.parkinglot.common.CarDto;
import jakarta.ejb.EJBException;
import jakarta.persistence.EntityManager;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
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
    public void createCar(String licensePlate, String parkingSpot, Long userID){
        LOG.info("createCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userID);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);

    }
    public CarDto findById(Long carId) {
        LOG.info("findById");
        Car car = entityManager.find(Car.class, carId);
        return new CarDto(
                car.getId(),
                car.getLicensePlate(),
                car.getParkingSpot(),
                car.getOwner().getUsername()
        );
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("updateCar");

        if (carId == null) {
            return;
        }

        User newOwner = entityManager.find(User.class, ownerId);
        Car car = entityManager.find(Car.class, carId);

        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        if (!newOwner.equals(car.getOwner())) {
            car.getOwner().getCars().remove(car);
            car.setOwner(newOwner);
            newOwner.getCars().add(car);
        }
    }

}
