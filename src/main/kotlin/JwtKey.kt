import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys

object JwtKey {
    val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
}