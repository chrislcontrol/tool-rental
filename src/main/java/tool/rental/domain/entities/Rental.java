package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A rental model that represents a tool rental by a friend.
 */
public class Rental extends Model {

    /**
     * The unique identifier of the rental.
     */
    private final String id;

    /**
     * The timestamp when the tool was rented.
     */
    private final long rentalTimestamp;

    /**
     * The timestamp when the tool was devolved.
     */
    private final long devolutionTimestamp;

    /**
     * The friend who rented the tool.
     */
    private final Friend friend;

    /**
     * The tool that was rented.
     */
    private final Tool tool;

    /**
     * Creates a new rental instance with the given id, rental timestamp, devolution timestamp, friend, and tool.
     *
     * @param id                  the unique identifier of the rental
     * @param rentalTimestamp     the timestamp when the tool was rented
     * @param devolutionTimestamp the timestamp when the tool was devolved
     * @param friend              the friend who rented the tool
     * @param tool                the tool that was rented
     */
    public Rental(String id, long rentalTimestamp, long devolutionTimestamp, Friend friend, Tool tool) {
        this.id = id;
        this.rentalTimestamp = rentalTimestamp;
        this.devolutionTimestamp = devolutionTimestamp;
        this.friend = friend;
        this.tool = tool;
    }

    /**
     * Returns the unique identifier of the rental.
     *
     * @return the id of the rental
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the timestamp when the tool was rented.
     *
     * @return the rental timestamp
     */
    public long getRentalTimestamp() {
        return rentalTimestamp;
    }

    /**
     * Returns the timestamp when the tool was devolved.
     *
     * @return the devolution timestamp
     */
    public long getDevolutionTimestamp() {
        return devolutionTimestamp;
    }

    /**
     * Returns the rental datetime as a Date object.
     *
     * @return the rental datetime
     */
    public Date getRentalDatetime() {
        long timestamp = getRentalTimestamp();
        return new Date(timestamp);
    }

    /**
     * Returns the devolution datetime as a Date object. If the devolution timestamp is 0, returns null.
     *
     * @return the devolution datetime
     */
    public Date getDevolutionDatetime() {
        long timestamp = getDevolutionTimestamp();

        return timestamp == 0 ? null : new Date(timestamp);
    }

    /**
     * Returns a formatted date string from a given Date object and format.
     *
     * @param date   the Date object to format
     * @param format the format to use
     * @return the formatted date string
     */
    private String getFormattedDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Returns the rental date as a formatted string using the given format.
     *
     * @param format the format to use
     * @return the formatted rental date string
     */
    public String getFormattedRentalDate(String format) {
        Date rentalDatetime = getRentalDatetime();
        return getFormattedDate(rentalDatetime, format);
    }

    /**
     * Returns the rental date as a formatted string using the default format "dd/MM/yyyy".
     *
     * @return the formatted rental date string
     */
    public String getFormattedRentalDate() {
        return getFormattedRentalDate("dd/MM/yyyy");
    }

    /**
     * Returns the devolution date as a formatted string using the given format. If the devolution timestamp is 0, returns an empty string.
     *
     * @param format the format to use
     * @return the formatted devolution date string
     */
    public String getFormattedDevolutionDate(String format) {
        Date devolutionDatetime = getDevolutionDatetime();
        if (devolutionDatetime == null) {
            return "";
        }
        return getFormattedDate(devolutionDatetime, format);
    }

    /**
     * Returns the devolution date as a formatted string using the default format "dd/MM/yyyy". If the devolution timestamp is 0, returns an empty string.
     *
     * @return the formatted devolution date string
     */
    public String getFormattedDevolutionDate() {
        return getFormattedDevolutionDate("dd/MM/yyyy");
    }

    /**
     * Returns the friend who rented the tool.
     *
     * @return the friend
     */
    public Friend getFriend() {
        return friend;
    }

    /**
     * Returns the tool that was rented.
     *
     * @return the tool
     */
    public Tool getTool() {
        return tool;
    }
}