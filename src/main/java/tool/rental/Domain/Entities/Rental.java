package tool.rental.Domain.Entities;

import tool.rental.Domain.Infra.DB.Contracts.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Rental extends Model {
    private final String id;
    private final int rentalTimestamp;
    private final int devolutionTimestamp;
    private final Friend friend;
    private final Tool tool;

    public Rental(String id, int rentalTimestamp, int devolutionTimestamp, Friend friend, Tool tool) {
        this.id = id;
        this.rentalTimestamp = rentalTimestamp;
        this.devolutionTimestamp = devolutionTimestamp;
        this.friend = friend;
        this.tool = tool;
    }

    public String getId() {
        return id;
    }

    public int getRentalTimestamp() {
        return rentalTimestamp;
    }

    public int getDevolutionTimestamp() {
        return devolutionTimestamp;
    }

    public LocalDateTime getRentalDatetime() {
        Instant instant = Instant.ofEpochSecond(this.rentalTimestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public String getFormattedRentalDate(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return this.getRentalDatetime().format(formatter);
    }

    public String getFormattedRentalDate() {
        return this.getFormattedRentalDate("dd/MM/yyyy");
    }

    public Friend getFriend() {
        return friend;
    }

    public Tool getTool() {
        return tool;
    }
}
