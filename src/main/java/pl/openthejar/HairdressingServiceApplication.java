package pl.openthejar;

import pl.openthejar.dao.ClientDao;
import pl.openthejar.dao.EmployeeDao;
import pl.openthejar.dao.EntityDao;
import pl.openthejar.misc.DatabaseService;
import pl.openthejar.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa zarzadzajaca konfiguracja do zarzadzania baza danych
 */
public class HairdressingServiceApplication {

    /**
     * Laduje przykladowe dane do bazy danych
     * @param args argumenty
     */
    public static void main(String[] args) {
        loadDummyData();
        loadMoreDummyData();
        loadNextDay();
        new DatabaseService();
    }

    /**
     * Laduje wolne terminy na nastepny dzien
     */
    private static void loadNextDay() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(DatabaseService.getDailyChecker());
        executor.shutdown();
    }

    /**
     * Manualnie sprawdza wykonanie wszystkich rezerwacji
     */
    private static void checkReservations() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(DatabaseService.getReservationsChecker());
        executor.shutdown();
    }

    /**
     * Laduje wiecej przykladowych danych
     */
    private static void loadMoreDummyData() {
        ClientDao clientDao = new ClientDao();
        Client client = clientDao.get(1L);

        Reservation reservation = new Reservation();
        reservation.setClient(client);

        Service service = new Service("Stylizacja2", 30, 100, 2);
        service.setProducts(new ArrayList<>(Arrays.asList(
                new Product(new EntityDao<>(ProductType.class).findAll().get(0), 31)
        )));

        reservation.setReview(new Review("Super", "Wszystko bardzo dobrze wykonane, swietna obsluga."));
        reservation.setService(service);

        WorkDate workDate = new WorkDate(new Date(System.currentTimeMillis()), new EmployeeDao().findAll().get(0));

        reservation.setWorkDate(workDate);

        EntityDao<Reservation> entityDao = new EntityDao<>(Reservation.class);
        entityDao.saveOrUpdate(reservation);
    }

    /**
     * Laduje przykladowe dane
     */
    private static void loadDummyData() {
        Client client = new Client("John", "Doe", 123123123L);
        client.setLogin("admin@wp.pl");
        client.setHash("admin");
        client.addDiscount(new Discount("Regular customer", 10));

        Reservation reservation = new Reservation();
        reservation.setClient(client);

        Service service = new Service("Farbowanie", 30, 40, 2);
        service.setProducts(new ArrayList<>(Arrays.asList(
                new Product(new ProductType("Szampon Matrix", 17), 32),
                new Product(new ProductType("Szampon Loreal", 12), 12),
                new Product(new ProductType("Odzywka Goldwell", 25), 41),
                new Product(new ProductType("Serum Loreal", 23), 32)
        )));

        Service service2 = new Service("Mezoterapia", 30, 120, 2);
        service2.setProducts(new ArrayList<>(Arrays.asList(
                new Product(new ProductType("Activ Aceton", 22), 15)
        )));

        reservation.setReview(new Review("Title", "Content"));
        reservation.setService(service);

        Reservation reservation2 = new Reservation();
        reservation2.setClient(client);
        reservation2.setService(service2);

        Employee employee = new Employee("employee1", "admin", "Ricardo", "Milos");
        WorkDate workDate = new WorkDate(new Date(1556015700L), employee);
        WorkDate workDate2 = new WorkDate(new Date(1556055700L), employee);

        reservation.setWorkDate(workDate);
        reservation2.setWorkDate(workDate2);

        EntityDao<Reservation> entityDao = new EntityDao<>(Reservation.class);
        entityDao.save(reservation);
        entityDao.save(reservation2);
    }
}