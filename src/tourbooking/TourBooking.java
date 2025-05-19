package tourbooking;

import java.time.LocalDate;

public abstract class TourBooking {
    protected String bookingId;
    protected String travelerName;
    protected LocalDate dateOfBirth;
    protected String nationality;
    protected String travelLocation;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String passportNumber;
    protected int groupSize;
    protected String hotelName;
    protected String preferredTourGuide;

    public TourBooking(String bookingId, String travelerName, LocalDate dateOfBirth,
                       String nationality, String travelLocation, LocalDate startDate,
                       LocalDate endDate, String passportNumber, int groupSize,
                       String hotelName, String preferredTourGuide) {
        this.bookingId = bookingId;
        this.travelerName = travelerName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.travelLocation = travelLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.passportNumber = passportNumber;
        this.groupSize = groupSize;
        this.hotelName = hotelName;
        this.preferredTourGuide = preferredTourGuide;
    }


    public abstract boolean validateTravelAge() throws Exception;
    public abstract boolean validateDates() throws Exception;
    public abstract boolean validateBookingLocation() throws Exception;
    public abstract double calculateBookingFee() throws Exception;


    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getTravelerName() { return travelerName; }
    public void setTravelerName(String travelerName) { this.travelerName = travelerName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getTravelLocation() { return travelLocation; }
    public void setTravelLocation(String travelLocation) { this.travelLocation = travelLocation; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }

    public int getGroupSize() { return groupSize; }
    public void setGroupSize(int groupSize) { this.groupSize = groupSize; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getPreferredTourGuide() { return preferredTourGuide; }
    public void setPreferredTourGuide(String preferredTourGuide) {
        this.preferredTourGuide = preferredTourGuide;
    }
}