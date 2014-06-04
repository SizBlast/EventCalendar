package json;

import domain.Event;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
public class EventListWritter implements MessageBodyWriter<List<Event>>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!List.class.isAssignableFrom(type)){
            return false;
        }
        if (genericType instanceof ParameterizedType){
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(Event.class);
        }
        else{
            return false;
        }
    }

    @Override
    public long getSize(List<Event> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Event> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonArrayBuilder jsonEvents = Json.createArrayBuilder();
        
        for (Event e : t)
        {
            JsonObjectBuilder jsonEvent = Json.createObjectBuilder();
            jsonEvent.add("name", e.getName());
        
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = e.getDate();
            jsonEvent.add("date", sdf.format(d));
        
            jsonEvent.add("category", e.getCategory());
            jsonEvent.add("place", e.getPlace());
            jsonEvent.add("price", e.getPrice());
            jsonEvent.add("organizer", e.getOrganizer());
            jsonEvent.add("startHour", e.getStartHour());
            jsonEvent.add("endHour", e.getEndHour());
            jsonEvent.add("website", e.getWebsite());
            
            jsonEvents.add(jsonEvent);
        }
        
        try(JsonWriter out = Json.createWriter(entityStream)){
            out.writeArray(jsonEvents.build());
        }
    }
}
