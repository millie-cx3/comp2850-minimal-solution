package utils

import java.util.UUID
import kotlin.random.Random

// session id in format P1_xxxx
fun createSessionId(): String {
    val chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890"
    val randomchars = (1..4).map { chars[Random.nextInt(chars.length)] }.joinToString("")
    return "P1_$randomchars"
}

/**
 * Session data for tracking requests (privacy-safe).
 *
 * **Privacy by design**:
 * - ID is randomly generated UUID
 * - No PII stored (no names, emails, IPs)
 * - Used only for metrics correlation
 * - Cannot be linked to individuals
 *
 * **Usage**:
 * ```kotlin
 * val session = call.sessions.get<SessionData>() ?: SessionData().also {
 *     call.sessions.set(it)
 * }
 * Logger.write(session.id, ...)
 * ```
 *
 * @property id Unique session identifier (UUID)
 */
data class SessionData(
    val id: String = createSessionId(),
)

/**
 * Generate a short session ID for logging (first 6 chars of UUID).
 * Used in console logs and CSV files for brevity.
 *
 * **Example**: `7a9f2c` instead of `7a9f2c3d-8b1e-4f5a-9c6d-2e1f3a4b5c6d`
 *
 * @param fullId Full UUID session ID
 * @return Short 6-character session ID
 */
fun shortSessionId(fullId: String): String = fullId.take(7)

/**
 * Generate a request ID for tracing individual requests within a session.
 * Used for detailed debugging and error tracking.
 *
 * **Format**: `r` + 8 random hex characters (e.g., `r_a3f7b2c1`)
 *
 * @return Request ID string
 */
fun generateRequestId(): String = "r_${UUID.randomUUID().toString().take(8)}"

fun newReqId(): String = generateRequestId()
