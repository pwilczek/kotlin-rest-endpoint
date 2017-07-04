package name.wilu.kotlinendpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(val lookup: LookupService) {

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> = response({ lookup.byId(id) })

    @GetMapping("/users/search")
    fun getByDetails(@RequestParam(required = false) id: String?,
                     @RequestParam(required = false) name: String?,
                     @RequestParam(required = false) surname: String?,
                     @RequestParam(required = false) mail: String?): ResponseEntity<*> {
        if (id == null && name == null && surname == null && mail == null)
            return ResponseEntity.badRequest().body("Missing search criteria!")
        return response({ lookup.byDetails(id, name, surname, mail) })
    }

    @PostMapping("/users/search/ids", headers = arrayOf("X-HTTP-Method-Override=GET"))
    fun getByIds(@RequestBody ids: List<String>): ResponseEntity<*> = response { lookup.byIds(ids) }


    private fun response(f: () -> List<User>): ResponseEntity<*> {
        val users = f.invoke()
        return when {
            users.isEmpty() -> ResponseEntity.notFound().build<Any>()
            else -> ResponseEntity.ok(users)
        }
    }
}