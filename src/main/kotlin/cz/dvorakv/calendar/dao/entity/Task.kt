package cz.dvorakv.calendar.dao.entity

import cz.dvorakv.calendar.constants.TaskStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

/**
 * Úkol s termínem (den, volitelně i čas), který patří jednomu z uživatelů
 * nebo je společný ([isShared] = true).
 */
@Entity
@Table(name = "tasks")
class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "title", nullable = false, length = 255)
    lateinit var title: String

    @Column(name = "note", columnDefinition = "TEXT")
    var note: String? = null

    @Column(name = "due_date", nullable = false)
    lateinit var dueDate: LocalDate

    /** NULL = úkol na celý den, jinak konkrétní čas. */
    @Column(name = "due_time")
    var dueTime: LocalTime? = null

    /** Komu úkol patří (ovlivňuje barvu a filtrování v UI). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    lateinit var owner: User

    /** Kdo záznam vytvořil (audit). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    lateinit var createdBy: User

    @Column(name = "is_shared", nullable = false)
    var isShared: Boolean = false

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: TaskStatus = TaskStatus.OPEN

    @Column(name = "completed_at")
    var completedAt: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_by_id")
    var completedBy: User? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null

}
