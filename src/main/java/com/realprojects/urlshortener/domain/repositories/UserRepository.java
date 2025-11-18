package com.realprojects.urlshortener.domain.repositories;

import com.realprojects.urlshortener.domain.entities.User;
import com.realprojects.urlshortener.domain.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

//Communicating DB using JDBC cLient
@Repository
public class UserRepository /*extends JpaRepository<User, Long>*/ {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password, name, role, created_at FROM users WHERE email = :email";
        return jdbcClient
                .sql(sql)//If the column names mentioned in sql query are same names as the @Column in entities,
                .param("email", email)//even "created_at" will be considered as "createdAt",
                //.query(User.class) //then we can add that entity in ".query()" and values will get mapped automatically
                .query(new UserRowMapper())//Else we may use a row mapper and get values from mapped by it.
                .optional();
    }

    public boolean existsByEmail(String email) {// Preparing sql query in the form of string
        String sql = "SELECT count(*) > 0 FROM users WHERE email = :email";
        return jdbcClient//Getting the string sql executed
                .sql(sql)
                .param("email", email)// appending te email to sql string which we receive as parameter from user
                .query(Boolean.class)
                .single();
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT id, email, password, name, role, created_at FROM users WHERE id = :id";
        return jdbcClient
                .sql(sql)
                .param("id", id)
                .query(new UserRowMapper())
                .optional();
    }

    public void save(User user) {
        String sql = """
                INSERT INTO users (email, password, name, role, created_at)
                VALUES (:email, :password, :name, :role, :createdAt)
                RETURNING id
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("name", user.getName())
                .param("role", user.getRole().name())
                .param("createdAt", Timestamp.from(user.getCreatedAt()))
                .update(keyHolder);//keyholder is used to update with default values
        Long userId = keyHolder.getKeyAs(Long.class);
        log.info("User saved with id: {}", userId);
    }

    static class UserRowMapper implements RowMapper<User> {
//CUSTOM FN. to map the values with our entity.
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            var user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            return user;
        }
    }
}

//Communicating db using JPA
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//
//    boolean existsByEmail(String email);
//}