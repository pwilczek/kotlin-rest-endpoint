package name.wilu.kotlinendpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.stereotype.Service
import javax.sql.DataSource

class User(val info: Map<String, String>)

@Service
class LookupService(val jdbc: JdbcTemplate) {
    fun byId(id: String): List<User> {
        return jdbc.query("select * from USER where id = '$id'", ResultSetExtractor { rs ->
            val users = mutableListOf<User>()
            val columns = (1..rs.metaData.columnCount).map { rs.metaData.getColumnName(it) }
            while (rs.next()) users.add(User(columns.map { it to rs.getString(it) }.toMap()))
            users
        })
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
    fun jdbcTemplate(ds: DataSource): JdbcTemplate = JdbcTemplate(ds)

}