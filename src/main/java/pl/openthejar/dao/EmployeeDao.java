package pl.openthejar.dao;

import pl.openthejar.misc.DateUtils;
import pl.openthejar.model.Employee;
import pl.openthejar.model.Reservation;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends EntityDao<Employee> {

    public EmployeeDao() {
        super(Employee.class);
    }

    public List<Reservation> getAllReservations(Employee employee) {
        final String query = "SELECT r FROM Reservation r";
        TypedQuery<Reservation> typedQuery = entityManager.createQuery(query, Reservation.class);

        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : typedQuery.getResultList()) {
            if (reservation.getWorkDate() != null
                    && DateUtils.isToday(reservation.getWorkDate().getDate())
                    && reservation.getWorkDate().getEmployees().contains(employee)) {
                result.add(reservation);
            }
        }

        return result;
    }
}