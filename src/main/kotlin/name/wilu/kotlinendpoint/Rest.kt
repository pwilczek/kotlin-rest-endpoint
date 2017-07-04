package name.wilu.kotlinendpoint

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(val lookup: LookupService) {

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> {
        val users: List<User> = lookup.byId(id)
        return when {
            users.isEmpty() -> ResponseEntity.notFound().build<Any>()
            else -> ResponseEntity.ok(users)
        }
    }

    @PostMapping("/users/search", headers = arrayOf("X-HTTP-Method-Override=GET"))
    fun getByIds(@RequestBody ids: List<String>): ResponseEntity<*> {
        println(ids)
        return ResponseEntity.ok().build<Any>()
    }
}




