package ticket.booking.entities;
import java.util.List;

public class User {

    private String username;
    private String password;
    private String userId;
    private List<Ticket> ticketsBooked;

    public User(){}
    public User(String username, String password, String userId, List<Ticket> ticketsBooked){
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.ticketsBooked = ticketsBooked;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return userId;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked){
        this.ticketsBooked = ticketsBooked;
    }
    public List<Ticket> getTicketBooked(){
        return ticketsBooked;
    }

}
