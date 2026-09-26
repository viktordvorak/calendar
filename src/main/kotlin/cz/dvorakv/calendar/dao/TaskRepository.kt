package cz.dvorakv.calendar.dao

import cz.dvorakv.calendar.dao.entity.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    /** Vrátí úkoly viditelné pro uživatele (jeho vlastní nebo shared) v daném rozsahu dat. */
    @Query(
        """
        select t from Task t
        where t.dueDate between :from and :to
          and (t.owner.id = :userId or t.isShared = true)
        order by t.dueDate asc, t.dueTime asc
        """
    )
    fun findVisibleInRange(
        @Param("userId") userId: Long,
        @Param("from") from: LocalDate,
        @Param("to") to: LocalDate
    ): List<Task>

}
