package cz.dvorakv.calendar.dao

import cz.dvorakv.calendar.dao.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface EventRepository : JpaRepository<Event, Long> {

    /** Vrátí události viditelné pro uživatele (jeho vlastní nebo shared) v daném rozsahu (UTC). */
    @Query(
        """
        select e from Event e
        where e.startsAt < :to and e.endsAt >= :from
          and (e.owner.id = :userId or e.isShared = true)
        order by e.startsAt asc
        """
    )
    fun findVisibleInRange(
        @Param("userId") userId: Long,
        @Param("from") from: Instant,
        @Param("to") to: Instant
    ): List<Event>

}
