package org.acme.reservation.reservation

import java.time.LocalDate

data class Reservation(var id: Long, var carId: Long, var startDay: LocalDate, var endDay: LocalDate) {
    fun checkReserved(): Boolean {
        return (!(this.endDay.isBefore(startDay) ||
                this.startDay.isAfter(endDay)))
    }
}