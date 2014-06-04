package json;

import domain.Event;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 * @author Christof Van Cauteren
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class EventReader implements MessageBodyReader<Event>
{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Event.class.isAssignableFrom(type);
    }

    @Override
    public Event readFrom(Class<Event> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try (JsonReader in = Json.createReader(entityStream)){
            Event e = new Event();
            JsonObject jsonEvent = in.readObject();
            e.setName(jsonEvent.getString("name", null));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = jsonEvent.getString("date", null);
            Date date = sdf.parse(dateString);
            e.setDate(date);
            
            e.setCategory(jsonEvent.getString("category", null));
            e.setPlace(jsonEvent.getString("place", null));
            e.setPrice(jsonEvent.getInt("price", 0));
            e.setOrganizer(jsonEvent.getString("organizer", null));
            e.setStartHour(jsonEvent.getString("startHour", null));
            e.setEndHour(jsonEvent.getString("endHour", null));
            e.setWebsite(jsonEvent.getString("website", null));
            
            return e;
        }
        catch(JsonException | ClassCastException | ParseException ex){
            throw new BadRequestException("Ongeldige JSON invoer");
        } 
    }
    
}
