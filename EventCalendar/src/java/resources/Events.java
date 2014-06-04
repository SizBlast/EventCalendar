package resources;

import domain.Event;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Christof Van Cauteren
 */

@Path("events")
@Transactional
public class Events 
{
    @PersistenceContext
    private EntityManager em;

    @Resource
    private Validator validator;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        TypedQuery tQuery = em.createNamedQuery("Event.findAll", Event.class);
        return tQuery.getResultList();
    }
    
    @GET
    @Path("/date")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEventsByDate(@QueryParam("q") String date) throws ParseException {
        TypedQuery<Event> tQuery = em.createNamedQuery("Event.findByDate", Event.class);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date queryDate = sdf.parse(date);
        tQuery.setParameter("date", queryDate);
        return tQuery.getResultList();
    }
    
    @GET
    @Path("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEventsByName(@QueryParam("q") String name) throws ParseException {
        TypedQuery<Event> tQuery = em.createNamedQuery("Event.findByName", Event.class);
        tQuery.setParameter("name", name.toUpperCase());
        return tQuery.getResultList();
    }
    
    @GET
    @Path("/place")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEventsByPlace(@QueryParam("q") String place) throws ParseException {
        TypedQuery<Event> tQuery = em.createNamedQuery("Event.findByPlace", Event.class);
        tQuery.setParameter("place", place.toUpperCase());
        return tQuery.getResultList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvent(Event e) {
        Set<ConstraintViolation<Event>> constraint = validator.validate(e);
        if (!constraint.isEmpty()) {
            throw new BadRequestException("Er hebben zich fouten voorgedaan.");
        }

        em.persist(e);
        return Response.created(URI.create("/" + e.getId())).build();
    }
}
