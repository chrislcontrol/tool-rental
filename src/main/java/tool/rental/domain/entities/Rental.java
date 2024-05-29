package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Rental extends Model {
    private final String id;
    private final long rentalTimestamp;
    private final long devolutionTimestamp;
    private final Friend friend;
    private final Tool tool;

    public Rental(String id, long rentalTimestamp, long devolutionTimestamp, Friend friend, Tool tool) {
        this.id = id;
        this.rentalTimestamp = rentalTimestamp;
        this.devolutionTimestamp = devolutionTimestamp;
        this.friend = friend;
        this.tool = tool;
    }

    public String getId() {
        return id;
    }

    public long getRentalTimestamp() {
        return rentalTimestamp;
    }

    public long getDevolutionTimestamp() {
        return devolutionTimestamp;
    }

    public Date getRentalDatetime() {
        long timestamp = getRentalTimestamp();
        return new Date(timestamp);
    }

    public Date getDevolutionDatetime() {
        long timestamp = getDevolutionTimestamp();

        return timestamp == 0 ? null : new Date(timestamp);
    }

    private String getFormattedDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String getFormattedRentalDate(String format) {
        Date rentalDatetime = getRentalDatetime();
        return getFormattedDate(rentalDatetime, format);
    }

    public String getFormattedRentalDate() {
        return getFormattedRentalDate("dd/MM/yyyy");
    }

    public String getFormattedDevolutionDate(String format) {
        Date devolutionDatetime = getDevolutionDatetime();
        if (devolutionDatetime == null) {
            return "";
        }
        return getFormattedDate(devolutionDatetime, format);
    }

    public String getFormattedDevolutionDate() {
        return getFormattedDevolutionDate("dd/MM/yyyy");
    }

    public Friend getFriend() {
        return friend;
    }

    public Tool getTool() {
        return tool;
    }
}
