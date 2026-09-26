package cz.dvorakv.calendar.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
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

/**
 * Událost s konkrétním časovým rozsahem (ukládáno v UTC), která patří
 * jednomu z uživatelů nebo je společná ([isShared] = true).
 */
@Entity
@Table(name = "events")
class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "title", nullable = false, length = 255)
    lateinit var title: String

    @Column(name = "note", columnDefinition = "TEXT")
    var note: String? = null

    /** Uloženo v UTC - konverze na Europe/Prague se řeší na hranici API/FE. */
    @Column(name = "starts_at", nullable = false)
    lateinit var startsAt: Instant

    @Column(name = "ends_at", nullable = false)
    lateinit var endsAt: Instant

    @Column(name = "all_day", nullable = false)
    var allDay: Boolean = false

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    lateinit var owner: User

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    lateinit var createdBy: User

    @Column(name = "is_shared", nullable = false)
    var isShared: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null

}
