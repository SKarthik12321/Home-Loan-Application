# JWT authentication flow (`feature/auth`)

This branch adds explicit documentation for the security slice used across the monolith.

1. **Register** — `POST /api/v1/auth/register` creates a `UserAccount` with a BCrypt hash (never store plaintext passwords).
2. **Login** — `POST /api/v1/auth/login` authenticates via Spring Security and returns a signed JWT (`AuthResponse.accessToken`).
3. **Protected APIs** — send `Authorization: Bearer <token>`; `JwtAuthFilter` validates the signature and expiry, then installs the `SecurityContext`.
4. **Admin-only** — `PATCH /api/v1/loans/{id}/status` requires `ROLE_ADMIN` (`@PreAuthorize`).

Secrets (`JWT_SECRET`, DB passwords) must come from `.env` or your CI/CD secret store — never commit `.env`.
