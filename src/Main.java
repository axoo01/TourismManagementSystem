import tourbooking.TourBooking;
import localindividual.LocalIndividualBooking;
import localgroup.LocalGroupBooking;
import internationalindividual.InternationalIndividualBooking;
import internationalgroup.InternationalGroupBooking;
import utils.ValidationUtils;
import utils.BookingValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("\n=== Tourism Management System ===");
                System.out.println("1. Local Individual Booking");
                System.out.println("2. Local Group Booking");
                System.out.println("3. International Individual Booking");
                System.out.println("4. International Group Booking");
                System.out.println("5. Exit");

                int choice = getIntInput("Enter your choice [1-5]: ");

                if (choice == 5) {
                    System.out.println("Thank you for using the Tourism Management System!");
                    break;
                }

                try {
                    switch (choice) {
                        case 1:
                            processLocalIndividualBooking();
                            break;
                        case 2:
                            processLocalGroupBooking();
                            break;
                        case 3:
                            processInternationalIndividualBooking();
                            break;
                        case 4:
                            processInternationalGroupBooking();
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (BookingValidationException e) {
                    System.out.println("Validation Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void processLocalIndividualBooking() throws BookingValidationException {
        System.out.println("\n=== Local Individual Booking ===");

        String bookingId = getValidInput("Enter booking ID: ", "Booking ID cannot be empty");
        String travelerName = getValidName("Enter traveler name: ");
        LocalDate dateOfBirth = getValidDateOfBirth("Enter date of birth (yyyy-MM-dd): ");
        String nationality = getValidInput("Enter nationality: ", "Nationality cannot be empty");
        String travelLocation = getValidLocation("Enter travel location (virunga/akagera/nyungwe): ", false);
        LocalDate startDate = getValidDate("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getValidDate("Enter end date (yyyy-MM-dd): ");
        validateTravelDates(startDate, endDate);
        String hotelName = getValidInput("Enter hotel name: ", "Hotel name cannot be empty");
        String preferredTourGuide = getInput("Enter preferred tour guide (optional): ");

        LocalIndividualBooking booking = new LocalIndividualBooking(
                bookingId,
                travelerName,
                dateOfBirth,
                nationality,
                travelLocation,
                startDate,
                endDate,
                null,
                1,
                hotelName,
                preferredTourGuide
        );

        validateAndCalculateFee(booking);
    }

    private static void processLocalGroupBooking() throws BookingValidationException {
        System.out.println("\n=== Local Group Booking ===");

        String bookingId = getValidInput("Enter booking ID: ", "Booking ID cannot be empty");
        String travelerName = getValidName("Enter group leader name: ");
        LocalDate dateOfBirth = getValidDateOfBirth("Enter group leader's date of birth (yyyy-MM-dd): ");
        String nationality = getValidInput("Enter nationality: ", "Nationality cannot be empty");
        String travelLocation = getValidLocation("Enter travel location (virunga/akagera/nyungwe): ", false);
        LocalDate startDate = getValidDate("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getValidDate("Enter end date (yyyy-MM-dd): ");
        validateTravelDates(startDate, endDate);
        int groupSize = getValidGroupSize("Enter group size (minimum 10): ", false);
        String hotelName = getValidInput("Enter hotel name: ", "Hotel name cannot be empty");
        String preferredTourGuide = getInput("Enter preferred tour guide (optional): ");

        LocalGroupBooking booking = new LocalGroupBooking(
                bookingId,
                travelerName,
                dateOfBirth,
                nationality,
                travelLocation,
                startDate,
                endDate,
                null,
                groupSize,
                hotelName,
                preferredTourGuide
        );

        validateAndCalculateFee(booking);
    }

    private static void processInternationalIndividualBooking() throws BookingValidationException {
        System.out.println("\n=== International Individual Booking ===");

        String bookingId = getValidInput("Enter booking ID: ", "Booking ID cannot be empty");
        String travelerName = getValidName("Enter traveler name: ");
        LocalDate dateOfBirth = getValidDateOfBirth("Enter date of birth (yyyy-MM-dd): ");
        String nationality = getValidInput("Enter nationality: ", "Nationality cannot be empty");
        String travelLocation = getValidLocation("Enter international travel location: ", true);
        LocalDate startDate = getValidDate("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getValidDate("Enter end date (yyyy-MM-dd): ");
        validateTravelDates(startDate, endDate);
        String passportNumber = getValidPassportNumber("Enter passport number: ");
        String hotelName = getValidInput("Enter hotel name: ", "Hotel name cannot be empty");
        String preferredTourGuide = getInput("Enter preferred tour guide (optional): ");

        InternationalIndividualBooking booking = new InternationalIndividualBooking(
                bookingId,
                travelerName,
                dateOfBirth,
                nationality,
                travelLocation,
                startDate,
                endDate,
                passportNumber,
                1,
                hotelName,
                preferredTourGuide
        );

        validateAndCalculateFee(booking);
    }

    private static void processInternationalGroupBooking() throws BookingValidationException {
        System.out.println("\n=== International Group Booking ===");

        String bookingId = getValidInput("Enter booking ID: ", "Booking ID cannot be empty");
        String travelerName = getValidName("Enter group leader name: ");
        LocalDate dateOfBirth = getValidDateOfBirth("Enter group leader's date of birth (yyyy-MM-dd): ");
        String nationality = getValidInput("Enter nationality: ", "Nationality cannot be empty");
        String travelLocation = getValidLocation("Enter international travel location: ", true);
        LocalDate startDate = getValidDate("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getValidDate("Enter end date (yyyy-MM-dd): ");
        validateTravelDates(startDate, endDate);
        String passportNumber = getValidPassportNumber("Enter passport number: ");
        int groupSize = getValidGroupSize("Enter group size (minimum 5): ", true);
        String hotelName = getValidInput("Enter hotel name: ", "Hotel name cannot be empty");
        String preferredTourGuide = getInput("Enter preferred tour guide (optional): ");

        InternationalGroupBooking booking = new InternationalGroupBooking(
                bookingId,
                travelerName,
                dateOfBirth,
                nationality,
                travelLocation,
                startDate,
                endDate,
                passportNumber,
                groupSize,
                hotelName,
                preferredTourGuide
        );

        validateAndCalculateFee(booking);
    }

    private static String getInputWithRetry(String prompt, String errorMessage, boolean validate, java.util.function.Function<String, Boolean> validator) throws BookingValidationException {
        String input = getInput(prompt);
        if (!validate || validator.apply(input)) {
            return input;
        }

        System.out.println("Error: " + errorMessage);
        System.out.println("You have one more try.");
        input = getInput(prompt);
        if (!validator.apply(input)) {
            throw new BookingValidationException(errorMessage);
        }
        return input;
    }

    private static String getValidInput(String prompt, String errorMessage) throws BookingValidationException {
        if (prompt.contains("booking ID")) {
            return getInputWithRetry(prompt, "Booking ID must be unique and cannot be empty", true, ValidationUtils::isValidBookingId);
        }
        return getInputWithRetry(prompt, errorMessage, true, input -> !input.isEmpty());
    }

    private static String getValidName(String prompt) throws BookingValidationException {
        return getInputWithRetry(prompt, "Name must contain only letters and spaces", true, ValidationUtils::isValidName);
    }

    private static LocalDate getValidDateOfBirth(String prompt) throws BookingValidationException {
        String dateStr = getInputWithRetry(prompt, "Invalid date of birth. Age must be between 20 and 50 years", true, ValidationUtils::isValidDateOfBirth);
        return ValidationUtils.parseDate(dateStr);
    }

    private static LocalDate getValidDate(String prompt) throws BookingValidationException {
        try {
            String dateStr = getInputWithRetry(prompt, "Invalid date format. Please use yyyy-MM-dd format", true,
                    input -> {
                        try {
                            ValidationUtils.parseDate(input);
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    });
            return ValidationUtils.parseDate(dateStr);
        } catch (Exception e) {
            throw new BookingValidationException("Invalid date format. Please use yyyy-MM-dd format");
        }
    }

    private static void validateTravelDates(LocalDate startDate, LocalDate endDate) throws BookingValidationException {
        if (!ValidationUtils.isValidTravelDates(startDate, endDate)) {
            System.out.println("Error: Invalid travel dates. Booking must be at least 3 days in advance, on weekdays only, and start date must be before end date.");
            System.out.println("You have one more try.");

            startDate = getValidDate("Enter start date again (yyyy-MM-dd): ");
            endDate = getValidDate("Enter end date again (yyyy-MM-dd): ");

            if (!ValidationUtils.isValidTravelDates(startDate, endDate)) {
                throw new BookingValidationException("Invalid travel dates. Booking must be at least 3 days in advance, on weekdays only, and start date must be before end date.");
            }
        }
    }


    private static String getValidPassportNumber(String prompt) throws BookingValidationException {
        return getInputWithRetry(prompt, "Invalid passport number. Must be 6-10 characters (letters and numbers only)",
                true, ValidationUtils::isValidPassportNumber);
    }

    private static int getValidGroupSize(String prompt, boolean isInternational) throws BookingValidationException {
        int minSize = isInternational ? 5 : 10;
        String errorMessage = "Invalid group size. Minimum size is " + minSize + " people";

        int groupSize = getIntInput(prompt + " [min: " + minSize + "]: ");
        if (ValidationUtils.isValidGroupSize(groupSize, isInternational)) {
            return groupSize;
        }

        System.out.println("Error: " + errorMessage);
        System.out.println("You have one more try.");
        groupSize = getIntInput(prompt + " [min: " + minSize + "]: ");

        if (!ValidationUtils.isValidGroupSize(groupSize, isInternational)) {
            throw new BookingValidationException(errorMessage);
        }
        return groupSize;
    }

    private static String getValidLocation(String prompt, boolean isInternational) throws BookingValidationException {
        String errorMessage = isInternational ?
                "Invalid location. Must be outside Rwanda" :
                "Invalid location. Must be one of: virunga, akagera, nyungwe";

        return getInputWithRetry(prompt, errorMessage, true,
                location -> ValidationUtils.isValidLocation(location, isInternational));
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void validateAndCalculateFee(TourBooking booking) throws BookingValidationException {
        try {
            System.out.println("\n=== Booking Details ===");
            System.out.println("----------------------------------------");
            System.out.println("1. Basic Information:");
            System.out.println("   Booking ID: " + booking.getBookingId());
            System.out.println("   Traveler Name: " + booking.getTravelerName());
            System.out.println("   Date of Birth: " + ValidationUtils.formatDate(booking.getDateOfBirth()));
            System.out.println("   Nationality: " + booking.getNationality());

            System.out.println("\n2. Travel Information:");
            System.out.println("   Travel Location: " + booking.getTravelLocation());
            System.out.println("   Start Date: " + ValidationUtils.formatDate(booking.getStartDate()));
            System.out.println("   End Date: " + ValidationUtils.formatDate(booking.getEndDate()));

            if (booking.getPassportNumber() != null) {
                System.out.println("\n3. International Details:");
                System.out.println("   Passport Number: " + booking.getPassportNumber());
            }

            if (booking.getGroupSize() > 1) {
                System.out.println("\n4. Group Information:");
                System.out.println("   Group Size: " + booking.getGroupSize());
            }

            System.out.println("\n5. Accommodation Details:");
            System.out.println("   Hotel Name: " + booking.getHotelName());
            if (booking.getPreferredTourGuide() != null && !booking.getPreferredTourGuide().isEmpty()) {
                System.out.println("   Preferred Tour Guide: " + booking.getPreferredTourGuide());
            }

            System.out.println("\n6. Fee Details:");
            double baseFee = booking.calculateBookingFee();
            System.out.println("   Base Fee: " + baseFee + " RWF");

            if (ValidationUtils.isRwandan(booking.getNationality())) {
                System.out.println("   Rwandan Discount (5%): -" + (baseFee * 0.05) + " RWF");
                baseFee *= 0.95;
            } else if (ValidationUtils.isEastAfricanCountry(booking.getNationality())) {
                System.out.println("   East African Discount (4%): -" + (baseFee * 0.04) + " RWF");
                baseFee *= 0.96;
            }

            System.out.println("   Final Fee: " + baseFee + " RWF");

            System.out.println("\n=== Validation Results ===");
            System.out.println("----------------------------------------");

            if (booking.validateTravelAge()) {
                System.out.println("Age validation passed (20-50 years)");
            }

            if (booking.validateDates()) {
                System.out.println("Date validation passed (weekdays only)");
            }

            if (booking.validateBookingLocation()) {
                System.out.println("Location validation passed");
            }
        } catch (Exception e) {
            throw new BookingValidationException("Validation failed: " + e.getMessage());
        }
    }
} 
