package name.wilu.kotlinendpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(val lookup: LookupService) {

    @GetMapping("users/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> {
        val users: List<User> = lookup.byId(id)
        return when {
            users.isEmpty() -> ResponseEntity.notFound().build<Any>()
            else -> ResponseEntity.ok(users)
        }
    }
}




