package domain;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import validation.ValidName;

/**
 * @author Christof Van Cauteren
 */

@Entity
@Table(name = "TBL_Events")
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findByDate", query = "SELECT e FROM Event e WHERE e.date = :date"),
    @NamedQuery(name = "Event.findByName", query = "SELECT e FROM Event e WHERE upper(e.name) like :name"),
    @NamedQuery(name = "Event.findByPlace", query = "SELECT e FROM Event e WHERE upper(e.place) = :place")
})
@TableGenerator(name="tab", initialValue=0, allocationSize=50)
public class Event 
{
    @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
    @Id
    private long id;
    @ValidName
    private String name;
    private String details;
    @NotNull(message = "Datum is verplicht.")
    @Temporal(TemporalType.DATE)
    private Date date;
    @NotNull(message = "Category is verplicht.")
    private String category;
    @NotNull(message = "Plaats is verplicht.")
    private String place;
    @NotNull(message = "Prijs is verplicht.")
    private int price;
    @NotNull(message = "Organisator is verplicht.")
    private String organizer;
    private String startHour;
    private String endHour;
    private String website;
    
    public Event() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
