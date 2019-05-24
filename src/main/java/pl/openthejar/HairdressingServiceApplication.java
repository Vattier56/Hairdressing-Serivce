package pl.openthejar;

import pl.openthejar.dao.ClientDao;
import pl.openthejar.dao.EmployeeDao;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.*;

import java.util.*;

public class HairdressingServiceApplication {

    public static void main(String[] args) {
//        EmployeeDao employeeDao = new EmployeeDao();
//        employeeDao.getAllReservations(employeeDao.get(11L));

//        loadDummyData();
//        loadMoreDummyData();
        System.exit(0);
    }

    private static void loadMoreDummyData() {
        Client client = new ClientDao().findAll().get(0);
        client.addDiscount(new Discount("Regular customer", 10));

        Reservation reservation = new Reservation();
        reservation.setClient(client);

        Service service = new Service("Fifth service", 600, 1000);
        service.setProducts(new ArrayList<>(Arrays.asList(
                new Product(new EntityDao<>(ProductType.class).findAll().get(0), 3),
                new Product(new EntityDao<>(ProductType.class).findAll().get(1), 2)
        )));

        reservation.setReview(new Review("Title", "Content"));
        reservation.setService(service);

        WorkDate workDate = new WorkDate(new Date(System.currentTimeMillis()), new EmployeeDao().findAll().get(0));

        reservation.setWorkDate(workDate);

        EntityDao<Reservation> entityDao = new EntityDao<>(Reservation.class);
        entityDao.saveOrUpdate(reservation);
        entityDao.cleanUp();
    }

    private static void loadDummyData() {
        Client client = new Client("John", "Doe", 123123123L);
        client.addDiscount(new Discount("Regular customer", 10));

        Reservation reservation = new Reservation();
        reservation.setClient(client);

        Service service = new Service("First service", 600, 1000);
        service.setProducts(new ArrayList<>(Arrays.asList(
                new Product(new ProductType("First product", 100), 3),
                new Product(new ProductType("Second product", 130), 2)
        )));

        reservation.setReview(new Review("Title", "Content"));
        reservation.setService(service);

        Employee employee = new Employee("employee1", "0x1acf2137ff", "Ricardo", "Milos");
        WorkDate workDate = new WorkDate(new Date(1556015700L), employee);

        reservation.setWorkDate(workDate);

        EntityDao<Reservation> entityDao = new EntityDao<>(Reservation.class);
        entityDao.save(reservation);
        entityDao.cleanUp();
    }
}
