package name.wilu.kotlinendpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.stereotype.Service
import javax.sql.DataSource

class User(val info: Map<String, String>)

@Service
class LookupService(val jdbc: NamedParameterJdbcTemplate) {
    //
    fun byIds(ids: List<String>): List<User> = jdbc.query("select * from USER where id in (:ids)",
            MapSqlParameterSource().addValue("ids", ids),
            ResultSetExtractor { rs ->
                val users = mutableListOf<User>()
                val columns = (1..rs.metaData.columnCount).map { rs.metaData.getColumnName(it) }
                while (rs.next()) users.add(User(columns.map { it to rs.getString(it) }.toMap()))
                users
            })

    fun byId(id: String): List<User> = byIds(listOf(id))

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