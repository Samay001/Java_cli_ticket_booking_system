package ticket.booking.entities;

public class Train {

    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String dateOfTravel;
    private final Integer totalSeats = 80;
    private Integer remainingSeats;

    public Train(String trainNumber,String trainName,String source,String destination,String dateOfTravel){
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
    }

    //getter
    public String getTrainNumber(){
        return trainNumber;
    }
    public String trainName(){
        return trainName;
    }
    public String source(){
        return source;
    }
    public String destination(){
        return destination;
    }
    public String dateOfTravel(){
        return dateOfTravel;
    }
    public Integer getRemainingSeats(){
        return remainingSeats;
    }

    //setter
    public void setTrainNumber(String trainNumber){
        this.trainNumber = trainNumber;
    }
    public void setTrainName(String trainName){
        this.trainName = trainName;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }
    public void setRemainingSeats(Integer remainingSeats){
        this.remainingSeats = remainingSeats;
    }
}
