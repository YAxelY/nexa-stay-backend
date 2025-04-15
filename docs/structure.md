hotel-management-backend/
├── pom.xml                                    # Parent POM (gestion des versions, dépendances communes)
├── gateway-service/                           # API Gateway (routeur, auth, load balancing)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/example/hotel/gatewayService/
│       │   │   ├── config/
│       │   │   │   └── GatewayConfig.java
│       │   │   └── filter/
│       │   │       └── AuthFilter.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/com/example/hotel/gatewayService/
│               └── GatewayServiceTests.java
├── user-service/                              # Gestion des utilisateurs (clients/hôteliers)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/example/hotel/userService/
│       │   │   ├── controller/
│       │   │   │   └── UserController.java
│       │   │   ├── service/
│       │   │   │   └── UserService.java
│       │   │   ├── repository/
│       │   │   │   └── UserRepository.java
│       │   │   ├── model/
│       │   │   │   └── User.java
│       │   │   └── config/
│       │   │       └── SecurityConfig.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/com/example/hotel/userService/
│               └── UserServiceTests.java
├── room-service/                              # Gestion des chambres
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/hotel/roomService/
│       │   ├── controller/RoomController.java
│       │   ├── service/RoomService.java
│       │   ├── repository/RoomRepository.java
│       │   └── model/Room.java
│       └── resources/application.yml
├── reservation-service/                       # Gestion des réservations
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/hotel/reservationService/
│       │   ├── controller/ReservationController.java
│       │   ├── service/ReservationService.java
│       │   ├── repository/ReservationRepository.java
│       │   └── model/Reservation.java
│       └── resources/application.yml
├── payment-service/                           # Traitement des paiements
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/hotel/paymentService/
│       │   ├── controller/PaymentController.java
│       │   ├── service/PaymentService.java
│       │   ├── repository/PaymentRepository.java
│       │   └── model/Payment.java
│       └── resources/application.yml
└── notification-service/                      # Envoi d’e-mails/SMS
    ├── pom.xml
    └── src/
        ├── main/java/com/example/hotel/notificationService/
        │   ├── service/NotificationService.java
        │   └── config/TwilioConfig.java
        └── resources/application.yml


keyclock