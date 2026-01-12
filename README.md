# FinSecure - Enterprise Finance Backend (Spring Boot)

FinSecure is an enterprise-grade backend system simulating NBFC/fintech internal workflows for secure loan and EMI management.

## Key Features
- Admin provisioning of system users (ADMIN / AGENT)
- First-login password enforcement
- JWT-based authentication & role-based authorization
- Loan creation with CIBIL-based eligibility rules
- Customer identity resolution using mobile number
- EMI schedule generation and EMI payment processing
- Customer risk profiling (BLACKLISTED handling)
- Audit logging for key actions
- Global exception handling & Bean Validation
- Reporting APIs for loans

## Tech Stack
- Java, Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Maven

## API Flow (High level)
1. Admin creates system users  
2. User login → JWT issued  
3. Admin creates loan  
4. Agent pays EMIs  
5. Reporting APIs for operations

> Note: Secrets are managed via environment variables (.env not committed).
