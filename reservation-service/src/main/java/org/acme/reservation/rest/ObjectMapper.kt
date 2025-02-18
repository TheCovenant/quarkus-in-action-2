package org.acme.reservation.rest

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET

@Path("/jackson/modules")
@ApplicationScoped
class JacksonTestResource @Inject constructor(
    private val objectMapper: ObjectMapper
) {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun checkModules(): String {
        return objectMapper.registeredModuleIds.toString() // Prints registered modules
    }
}