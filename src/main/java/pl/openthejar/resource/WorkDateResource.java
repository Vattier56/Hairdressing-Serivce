package pl.openthejar.resource;

import pl.openthejar.dao.EntityDao;
import pl.openthejar.model.WorkDate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Endpoint terminu
 */
@Path("/work-dates")
public class WorkDateResource {

    private EntityDao<WorkDate> dao = new EntityDao<>(WorkDate.class);

    /**
     * Pobiera wszystkie terminy
     * @return Lista wszystkich terminow
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkDate> getAll() {
        return dao.findAll();
    }

    /**
     * Zapisuje nowy termin w bazie danych
     * @param workDate Obiekt bazy danych do zapisania
     * @return Zapisany termin
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WorkDate save(WorkDate workDate) {
        return dao.save(workDate);
    }
}
