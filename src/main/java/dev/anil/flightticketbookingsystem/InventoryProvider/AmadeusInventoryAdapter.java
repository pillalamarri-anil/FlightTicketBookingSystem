package dev.anil.flightticketbookingsystem.InventoryProvider;

import dev.anil.flightticketbookingsystem.models.*;
import dev.anil.flightticketbookingsystem.models.enums.BookingStatus;
import dev.anil.flightticketbookingsystem.models.enums.FlightSeatStatus;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AmadeusInventoryAdapter implements InventoryAdapter {

    private final AmadeusAuth authService;
    private final ObjectMapper mapper = new ObjectMapper();

    public AmadeusInventoryAdapter(AmadeusAuth authService) {
        this.authService = authService;
    }

    @Override
    public Flight[] getFlights(City source, City destination, Date departureDate) {

        try {
            String token = authService.getAccessToken();

            String url = String.format(
                    "https://test.api.amadeus.com/v2/shopping/flight-offers" +
                            "?originLocationCode=%s" +
                            "&destinationLocationCode=%s" +
                            "&departureDate=%s" +
                            "&adults=1" +
                            "&max=50",
                    source.getCode(),
                    destination.getCode(),
                    formatDate(departureDate)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            return parseFlights(response.body());

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch flights", e);
        }
    }

    private Flight[] parseFlights(String json) throws Exception {

        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");

        Map<String, Flight> uniqueFlights = new LinkedHashMap<>();

        for (JsonNode offer : data) {

            long price = offer.get("price").get("total").asLong();

            for (JsonNode itinerary : offer.get("itineraries")) {
                for (JsonNode segment : itinerary.get("segments")) {

                    String flightNumber =
                            segment.get("carrierCode").asText() +
                                    segment.get("number").asText();

                    String key = flightNumber + segment.get("departure").get("at").asText();

                    uniqueFlights.computeIfAbsent(key, k -> {

                        Flight flight = new Flight();
                        flight.setFlightNumber(flightNumber);

                        flight.setDepartureTime(
                                Instant.parse(segment.get("departure").get("at").asText())
                                        .toEpochMilli()
                        );

                        flight.setArrivalTime(
                                Instant.parse(segment.get("arrival").get("at").asText())
                                        .toEpochMilli()
                        );

                        City src = new City();
                        src.setCode(segment.get("departure").get("iataCode").asText());
                        flight.setSource(src);

                        City dst = new City();
                        dst.setCode(segment.get("arrival").get("iataCode").asText());
                        flight.setDestination(dst);

                        Operator operator = new Operator();
                        operator.setName(segment.get("carrierCode").asText());
                        flight.setOperator(operator);

                        flight.setMinPrice(price);
                        return flight;
                    });

                    Flight f = uniqueFlights.get(key);
                    f.setMinPrice(Math.min(f.getMinPrice(), price));
                }
            }
        }

        return uniqueFlights.values().toArray(new Flight[0]);
    }

    private String formatDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }


    @Override
    public Flight_Seat[] ShowSeats(Flight flight) {

        try {
            String token = authService.getAccessToken();

            String url = String.format(
                    "https://test.api.amadeus.com/v1/shopping/seatmaps" +
                            "?carrierCode=%s" +
                            "&flightNumber=%s" +
                            "&origin=%s" +
                            "&destination=%s" +
                            "&departureDate=%s",
                    flight.getOperator().getName(),
                    extractFlightNumber(flight),
                    flight.getSource().getCode(),
                    flight.getDestination().getCode(),
                    formatDate(flight.getDepartureTime())
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            return parseSeats(response.body(), flight);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch seat map", e);
        }
    }

    private Flight_Seat[] parseSeats(String json, Flight flight) throws Exception {

        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");

        List<Flight_Seat> seats = new ArrayList<>();

        for (JsonNode seatMap : data) {
            for (JsonNode deck : seatMap.get("decks")) {
                for (JsonNode row : deck.get("seats")) {
                    for (JsonNode seatNode : row.get("seats")) {

                        Seat seat = new Seat();
                        seat.setSeatNumber(seatNode.get("number").asText());
                        //seat.setSeatType(mapSeatType(seatNode));
                        seat.setAirCraft(flight.getAirCraft());

                        Flight_Seat fs = new Flight_Seat();
                        fs.setFlight(flight);
                        fs.setSeat(seat);
                        fs.setStatus(mapSeatStatus(seatNode));
                        fs.setPrice(0.0f); // seat price rarely available

                        seats.add(fs);
                    }
                }
            }
        }
        return seats.toArray(new Flight_Seat[0]);
    }

    private FlightSeatStatus mapSeatStatus(JsonNode seatNode) {

        if (!seatNode.has("availabilityStatus")) {
            return FlightSeatStatus.UNKNOWN;
        }

        return "AVAILABLE".equals(seatNode.get("availabilityStatus").asText())
                ? FlightSeatStatus.AVAILABLE
                : FlightSeatStatus.BOOKED;
    }


    private String extractFlightNumber(Flight flight) {
        // AI203 → 203
        return flight.getFlightNumber().replaceAll("[^0-9]", "");
    }

    private String formatDate(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }


    private ObjectNode buildFlightOrderRequest(
            User user,
            List<Flight_Seat> seats,
            ObjectMapper mapper) {

        ObjectNode root = mapper.createObjectNode();
        ArrayNode travelers = mapper.createArrayNode();

        ObjectNode traveler = mapper.createObjectNode();
        traveler.put("id", "1");
        //traveler.put("dateOfBirth", user.getDob());
        //traveler.put("gender", user.getGender());

        ObjectNode name = mapper.createObjectNode();
        //name.put("firstName", user.getFirstName());
        //name.put("lastName", user.getLastName());
        traveler.set("name", name);

        travelers.add(traveler);
        root.set("travelers", travelers);

        // Seat preference (best-effort)
        ArrayNode seatSelections = mapper.createArrayNode();
        for (Flight_Seat fs : seats) {
            ObjectNode seat = mapper.createObjectNode();
            seat.put("seatNumber", fs.getSeat().getSeatNumber());
            seatSelections.add(seat);
        }
        root.set("seatSelections", seatSelections);

        // IMPORTANT: flightOffer must come from price confirmation step
        //root.set("flightOffers", getConfirmedFlightOffers());

        return root;
    }


    @Override
    public Booking[] BookTicket(User user, List<Flight_Seat> flightSeatList) {

        try {
            String token = authService.getAccessToken();

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode requestBody =
                    buildFlightOrderRequest(user, flightSeatList, mapper);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://test.api.amadeus.com/v1/booking/flight-orders"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            mapper.writeValueAsString(requestBody)
                    ))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            return parseBookingResponse(response.body(), user, flightSeatList);

        } catch (Exception e) {
            throw new RuntimeException("Booking failed", e);
        }
    }

    private Booking[] parseBookingResponse(
            String json,
            User user,
            List<Flight_Seat> seats
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");

        Booking booking = new Booking();
        booking.setUser(user);
        //booking.setPnr(data.get("id").asText());
        booking.setStatus(BookingStatus.CONFIRMED);
        //booking.setBookingTime(System.currentTimeMillis());

        for (Flight_Seat fs : seats) {
            fs.setBooking(booking);
            fs.setStatus(FlightSeatStatus.BOOKED);
        }

        //booking.setFlightSeats(seats);
        return new Booking[]{ booking };
    }

}





