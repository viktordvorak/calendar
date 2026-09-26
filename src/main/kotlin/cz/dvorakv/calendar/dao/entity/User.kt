package cz.dvorakv.calendar.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

/**
 * Registrovaný uživatel aplikace (jeden ze dvou povolených Google účtů).
 */
@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /** Stabilní identifikátor "sub" z Google OIDC - používá se pro autorizaci. */
    @Column(name = "google_sub", nullable = false, unique = true, length = 255)
    lateinit var googleSub: String

    @Column(name = "email", nullable = false, unique = true, length = 255)
    lateinit var email: String

    @Column(name = "display_name", nullable = false, length = 100)
    lateinit var displayName: String

    /** Hex barva pro odlišení v kalendáři, např. "#3B82F6". */
    @Column(name = "color", nullable = false, length = 7)
    lateinit var color: String

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null

}
