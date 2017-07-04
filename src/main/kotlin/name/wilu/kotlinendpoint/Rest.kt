package name.wilu.kotlinendpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(val lookup: LookupService) {

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> = response({ lookup.byId(id) })

    @PostMapping("/users/search", headers = arrayOf("X-HTTP-Method-Override=GET"))
    fun getByIds(@RequestBody ids: List<String>): ResponseEntity<*> = response { lookup.byIds(ids) }


    private fun response(f: () -> List<User>): ResponseEntity<*> {
        val users = f.invoke()
        return when {
            users.isEmpty() -> ResponseEntity.notFound().build<Any>()
            else -> ResponseEntity.ok(users)
        }
    }
}




