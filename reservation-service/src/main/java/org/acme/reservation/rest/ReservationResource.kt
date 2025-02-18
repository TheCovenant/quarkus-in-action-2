package org.acme.reservation.rest

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.reservation.inventory.Car
import org.acme.reservation.inventory.InventoryClient
import org.acme.reservation.reservation.Reservation
import org.acme.reservation.reservation.ReservationsRepository
import org.jboss.resteasy.reactive.RestQuery
import java.time.LocalDate

@Path("reservation")
@Produces(MediaType.APPLICATION_JSON)
class ReservationResource(
    private val reservationsRepository: ReservationsRepository,
    private val inventoryClient: InventoryClient
) {
    @GET
    @Path("availability")
    fun availability(
        @RestQuery startDate: LocalDate?,
        @RestQuery endDate: LocalDate?
    ): Collection<Car> {
        // obtain all cars from inventory
        val availableCars: List<Car> = inventoryClient.allCars()
        // create a map from id to car
        val carsById = mutableMapOf<Long, Car>()
        for (car in availableCars) {
            carsById[car.id] = car
        }
        // get all current reservations
        val reservations: List<Reservation> = reservationsRepository.findAll()
        // for each reservation, remove the car from the map
        for (reservation in reservations) {
            if (reservation.checkReserved()) {
                carsById.remove(reservation.carId)
            }
        }
        return carsById.values
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    fun make(reservation: Reservation): Reservation {
        reservationsRepository.save(reservation)
        return reservation
    }

    @GET
    @Path("test")
    fun test() = "hiya"
}