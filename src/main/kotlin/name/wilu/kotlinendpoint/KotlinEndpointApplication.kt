package name.wilu.kotlinendpoint

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class KotlinEndpointApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinEndpointApplication::class.java, *args)
}