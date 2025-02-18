import jakarta.inject.Singleton
import org.acme.reservation.reservation.Reservation
import org.acme.reservation.reservation.ReservationsRepository
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

@Singleton
class InMemoryReservationsRepository : ReservationsRepository {
    private val ids: AtomicLong = AtomicLong(0)
    private val store: MutableList<Reservation> = mutableListOf()
    override fun findAll(): List<Reservation> {
        return store.toList()
    }

    override fun save(reservation: Reservation): Reservation {
        reservation.id = ids.incrementAndGet()
        store.add(reservation)
        return reservation
    }
}