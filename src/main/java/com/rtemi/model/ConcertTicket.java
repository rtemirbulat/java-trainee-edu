package com.rtemi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rtemi.annotation.NullableWarning;
import com.rtemi.interfaces.Printable;
import com.rtemi.interfaces.Shareable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcertTicket extends TicketUID implements Printable, Shareable {
    private static final int ID_MAX_DIGITS_ALLOWED = 4;
    private static final int HALL_NAME_MAX_CHARS = 10;
    private static final int PRICE_PRECISION = 9;
    private static final int WEIGHT_PRECISION = 3;

    private String concertTicketId;
    @NullableWarning(key = "Variable [concertHall] is null in [Ticket]!")
    private String concertHall;
    private int eventCode;
    private long ticketPurchaseTime;
    private long time;
    private boolean isPromo;
    private char sector;
    private BigDecimal maxWeight;
    private BigDecimal ticketPrice;


    public ConcertTicket() {
    }

    public ConcertTicket(String ticketId, String concertHall, int eventCode, boolean isPromo, char sector, BigDecimal maxWeight, BigDecimal ticketPrice) {
        this.concertTicketId = ticketId.length() > ID_MAX_DIGITS_ALLOWED ? ticketId.substring(0, ID_MAX_DIGITS_ALLOWED) : ticketId;
        this.concertHall = concertHall.length() > HALL_NAME_MAX_CHARS ? concertHall.substring(0, HALL_NAME_MAX_CHARS) : concertHall;
        this.eventCode = Integer.parseInt(String.valueOf(eventCode).substring(0, 3));
        this.isPromo = isPromo;
        this.maxWeight = maxWeight;
        this.ticketPrice = ticketPrice;
        setTicketPurchaseTime();
        setSector(sector);

    }

    public ConcertTicket(String concertHall, int eventCode) {
        this.concertHall = concertHall.length() > HALL_NAME_MAX_CHARS ? concertHall.substring(0, HALL_NAME_MAX_CHARS) : concertHall;
        this.eventCode = Integer.parseInt(String.valueOf(eventCode).substring(0, 3));
        setTicketPurchaseTime();
    }

    public String getConcertTicketId() {
        return concertTicketId;
    }

    public void setTicketPurchaseTime(){
        this.ticketPurchaseTime = Instant.now().getEpochSecond();
    }

    public void setTicketTime(long time) {
        this.time = time;
    }

    public void setSector(char sector) {
        if (sector == 'A' || sector == 'B' || sector == 'C') {
            this.sector = sector;
        } else {
            System.out.println("Sector must be one of following: A, B, C");
        }
    }

    public char getSector() {
        return sector;
    }

    public String getAllValues(){
        return toString();
    }
    @Override
    public String toString() {
        return "Ticket " +
                "concertTicketId='" + concertTicketId + '\'' +
                ", concertHall - '" + concertHall + '\'' +
                ", eventCode - " + eventCode +
                ", ticketPurchaseTime - " +  ticketPurchaseTime +
                ", ticketTime - " + time +
                ", isPromo - " + isPromo +
                ", sector - " + sector +
                ", maxWeight - " +  maxWeight.setScale(WEIGHT_PRECISION,RoundingMode.HALF_UP) +
                ", ticketPrice - " + ticketPrice.setScale(PRICE_PRECISION,RoundingMode.HALF_UP);
    }

    @Override
    public void printContent() {
        System.out.println(toString());
    }

    @Override
    public void share(String phone) {

    }

    @Override
    public void share(String phone, String email) {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcertTicket)) return false;
        ConcertTicket ticket = (ConcertTicket) o;
        return eventCode == ticket.eventCode &&
                ticketPurchaseTime == ticket.ticketPurchaseTime &&
                time == ticket.time &&
                isPromo == ticket.isPromo &&
                sector == ticket.sector &&
                Objects.equals(concertTicketId, ticket.concertTicketId) &&
                Objects.equals(concertHall, ticket.concertHall) &&
                Objects.equals(maxWeight, ticket.maxWeight) &&
                Objects.equals(ticketPrice, ticket.ticketPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertTicketId, concertHall, eventCode, ticketPurchaseTime,time, isPromo, sector, maxWeight, ticketPrice);
    }
}
