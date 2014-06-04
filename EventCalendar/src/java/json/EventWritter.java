package json;

import domain.Event;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * @author Christof Van Cauteren
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class EventWritter implements MessageBodyWriter<Event>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Event.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Event t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Event t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonObjectBuilder jsonEvent = Json.createObjectBuilder();
        
        jsonEvent.add("name", t.getName());
        jsonEvent.add("details", t.getDetails());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jsonEvent.add("date", sdf.format(t.getDate()));
        
        jsonEvent.add("category", t.getCategory());
        jsonEvent.add("place", t.getPlace());
        jsonEvent.add("price", t.getPrice());
        jsonEvent.add("organizer", t.getOrganizer());
        jsonEvent.add("startHour", t.getStartHour());
        jsonEvent.add("endHour", t.getEndHour());
        jsonEvent.add("website", t.getWebsite());
        
        try (JsonWriter out = Json.createWriter(entityStream)){
            out.writeObject(jsonEvent.build());
        }
    }
}
