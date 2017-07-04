package name.wilu.kotlinendpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.stereotype.Service
import java.sql.ResultSet
import javax.sql.DataSource

class User(val info: Map<String, String>)

@Service
class LookupService(val jdbc: NamedParameterJdbcTemplate) {
    //
    fun byIds(ids: List<String>): List<User> {
        return jdbc.query("select * from USER where id in (:ids)",
                MapSqlParameterSource().addValue("ids", ids),
                UserExtractor)
    }
    //
    fun byId(id: String): List<User> = byIds(listOf(id))
    //
    fun byDetails(id: String?, name: String?, surname: String?, mail: String?): List<User> {
        val query = "select * from USER where " +
                mapOf(
                        IdCondition to id,
                        NameCondition to name,
                        SurnameCondition to surname,
                        MailCondition to mail
                ).filterValues { !it.isNullOrBlank() }
                        .map { it.key.build(it.value!!) }
                        .joinToString(separator = " and ")
        return jdbc.query(query, UserExtractor)
    }
    //
    abstract class Condition(val column: String) {
        fun build(pattern: String): String = "$column LIKE '%$pattern%'"
    }
    //
    object IdCondition : Condition("id")
    object NameCondition : Condition("name")
    object SurnameCondition : Condition("surname")
    object MailCondition : Condition("mail")
    //
    object UserExtractor : ResultSetExtractor<List<User>> {
        override fun extractData(rs: ResultSet): List<User> {
            val users = mutableListOf<User>()
            val columns = (1..rs.metaData.columnCount).map { rs.metaData.getColumnName(it) }
            while (rs.next()) users.add(User(columns.map { it to rs.getString(it) }.toMap()))
            return users
        }
    }

}

@Configuration
class Configuration {

    @Bean
    fun dataSource(): DataSource =
            EmbeddedDatabaseBuilder()
                    .setName("embedded")
                    .setType(EmbeddedDatabaseType.H2)
                    .addDefaultScripts()
                    .build()

    @Bean
    fun namedParameterJdbcTemplate(ds: DataSource): NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(ds)

}