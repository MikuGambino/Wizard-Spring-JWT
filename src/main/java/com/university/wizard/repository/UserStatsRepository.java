package com.university.wizard.repository;

import com.university.wizard.model.UserStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface UserStatsRepository extends ListCrudRepository<UserStats, Long> {

    @Query("""
            select us
            from UserStats us
            join User u on u = us.user
            where u.username = :username
            """)
    UserStats findUserStatsByUsername(String username);

}
