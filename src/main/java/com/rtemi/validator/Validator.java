package com.rtemi.validator;

import com.rtemi.loaders.JsonDataLoader;
import com.rtemi.model.BusTicket;
import com.rtemi.model.enums.ErrorType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;


public class Validator {
    public static final Logger LOGGER = getLogger(Validator.class.getSimpleName());
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Map<BusTicket, ErrorType> errorMap = new ConcurrentHashMap<>();

    public void checkType(BusTicket ticket) {
        if (ticket.getTicketType() == null) {
            LOGGER.log(Level.WARNING, "Bus Ticket Type not found");
            createError(ticket, ErrorType.TICKET_TYPE);
        } else {
            if (!Arrays.asList("DAY", "WEEK", "YEAR").contains(ticket.getTicketType())) {
                checkStartDate(ticket);
                LOGGER.log(Level.WARNING, "Bus Ticket Type other than DAY,WEEK or YEAR cannot contain StartDate");
                createError(ticket, ErrorType.START_DATE);
            }
        }
    }

    public void checkStartDate(BusTicket ticket) {
        LocalDate currentDate = LocalDate.now();
        if (ticket.getStartDate() == null || ticket.getStartDate().isEmpty()) {
            LOGGER.log(Level.WARNING, "Bus Ticket Date not found!");
            createError(ticket, ErrorType.START_DATE);
        } else {
            if (LocalDate.parse(ticket.getStartDate()).isAfter(currentDate)) {
                LOGGER.log(Level.WARNING, "Bus Ticket Date can't be in the future!");
                createError(ticket, ErrorType.START_DATE);
            }
        }
    }

    public void checkPriceOfTicket(BusTicket ticket) {
        if (ticket.getPrice() == null || ticket.getPrice().intValueExact() == 0) {
            LOGGER.log(Level.WARNING, "BusTicket price may be null or zero");
            createError(ticket, ErrorType.PRICE);
        } else if (ticket.getPrice().intValueExact() % 2 != 0) {
            LOGGER.log(Level.WARNING, "BusTicket price is not even");
            createError(ticket, ErrorType.PRICE);
        }
    }

    private void createError(BusTicket ticket, ErrorType errorType) {
        ticket.setTicketUid(idCounter.incrementAndGet());
        errorMap.put(ticket, errorType);
    }

    private void aggregateBusTicketErrors(List busTickets, Map errorMap) {
        long total = busTickets.stream().count();
        long invalidTicketsCount = errorMap.keySet().stream()
                .distinct().count();
        long numOfDateErrors = errorMap.values().stream()
                .filter(err -> err.equals(ErrorType.START_DATE)).count();
        long numOfPriceErrors = errorMap.values().stream()
                .filter(err -> err.equals(ErrorType.PRICE)).count();
        long numOfTypeErrors = errorMap.values().stream()
                .filter(err -> err.equals(ErrorType.TICKET_TYPE)).count();
        long validTicketsCount = total - invalidTicketsCount;
        String mostFrequent;
        if (numOfDateErrors > numOfPriceErrors && numOfDateErrors > numOfTypeErrors) {
            mostFrequent = "StartDate error";
        } else if (numOfPriceErrors > numOfDateErrors && numOfPriceErrors > numOfTypeErrors) {
            mostFrequent = "Price error";
        } else if (numOfTypeErrors > numOfDateErrors && numOfTypeErrors > numOfPriceErrors) {
            mostFrequent = "TicketType error";
        } else {
            mostFrequent = "Equal occurrence of error types";
        }

        System.out.println("bus tickets in total : " + total);
        System.out.println("valid tickets in total : " + validTicketsCount);
        System.out.println("invalid tickets : " + invalidTicketsCount);
        System.out.println("number of date errors : " + numOfDateErrors);
        System.out.println("number of price errors : " + numOfPriceErrors);
        System.out.println("number of type errors : " + numOfTypeErrors);
        System.out.println("most frequent error : " + mostFrequent);
    }


    public static void main(String[] args) throws IOException {
        String pathToSource = "src/main/resources/busTicket.json";
        JsonDataLoader<BusTicket> busTicketJsonDataLoader = new JsonDataLoader<>(pathToSource, BusTicket.class);
        List<BusTicket> busTickets = busTicketJsonDataLoader.getDataList();
        busTickets.forEach(busTicket -> LOGGER.log(Level.INFO, busTicket.toString()));

        Validator validator = new Validator();
        busTickets.forEach(ticket -> {
            validator.checkStartDate(ticket);
            validator.checkType(ticket);
            validator.checkPriceOfTicket(ticket);
        });

        if (errorMap.size() > 1) {
            LOGGER.log(Level.ALL, "More than 1+ validation errors occurred");
            validator.aggregateBusTicketErrors(busTickets, errorMap);
        }

    }
}
