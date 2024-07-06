package com.rtemi.model;

import java.math.BigDecimal;
import java.util.Objects;

public class BusTicket extends TicketUID {
    String ticketClass;
    String ticketType;
    String startDate;
    BigDecimal price;

    public BusTicket() {
    }

    public BusTicket(String ticketClass, String ticketType, String startDate, BigDecimal price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    public BusTicket(int ticketUid, String ticketClass, String ticketType, String startDate, BigDecimal price) {
        super(ticketUid);
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int getTicketUid() {
        return super.getTicketUid();
    }

    @Override
    public void setTicketUid(int ticketUid) {
        super.setTicketUid(ticketUid);
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "ticketClass='" + ticketClass + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusTicket busTicket = (BusTicket) o;
        return Objects.equals(ticketClass, busTicket.ticketClass) && Objects.equals(ticketType, busTicket.ticketType) && Objects.equals(startDate, busTicket.startDate) && Objects.equals(price, busTicket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketClass, ticketType, startDate, price);
    }

}
