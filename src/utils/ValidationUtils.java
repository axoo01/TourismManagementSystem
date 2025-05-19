package utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern PASSPORT_PATTERN = Pattern.compile("^[A-Z0-9]{6,10}$");
    private static final List<String> EAST_AFRICAN_COUNTRIES = Arrays.asList(
            "kenya", "uganda", "tanzania", "burundi", "south sudan"
    );
    private static final List<String> RWANDA_LOCATIONS = Arrays.asList(
            "virunga", "akagera", "nyungwe"
    );
    private static final Set<String> usedBookingIds = new HashSet<>();

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidDateOfBirth(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDate now = LocalDate.now();
            int age = Period.between(date, now).getYears();
            return age >= 20 && age <= 50;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidTravelDates(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        return startDate != null && endDate != null &&
                ChronoUnit.DAYS.between(today, startDate) >= 3 &&
                !startDate.isAfter(endDate) &&
                !isWeekend(startDate) &&
                !isWeekend(endDate);
    }


    private static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static boolean isValidPassportNumber(String passportNumber) {
        return passportNumber != null && PASSPORT_PATTERN.matcher(passportNumber).matches();
    }

    public static boolean isValidGroupSize(int groupSize, boolean isInternational) {
        return isInternational ? groupSize >= 5 : groupSize >= 10;
    }

    public static boolean isValidLocation(String location, boolean isInternational) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        location = location.toLowerCase();
        return isInternational ? !RWANDA_LOCATIONS.contains(location) : RWANDA_LOCATIONS.contains(location);
    }

    public static boolean isEastAfricanCountry(String nationality) {
        return nationality != null && EAST_AFRICAN_COUNTRIES.contains(nationality.toLowerCase());
    }

    public static boolean isRwandan(String nationality) {
        return nationality != null && nationality.equalsIgnoreCase("rwanda");
    }

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }

    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static boolean isValidBookingId(String bookingId) {
        if (bookingId == null || bookingId.isEmpty()) {
            return false;
        }
        synchronized (usedBookingIds) {
            if (usedBookingIds.contains(bookingId)) {
                return false;
            }
            usedBookingIds.add(bookingId);
            return true;
        }
    }
}