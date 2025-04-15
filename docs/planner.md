

Voici une proposition de **Product Backlog** et de **Sprint Backlogs** (4 sprints de 1 semaine pour un projet d’1 mois) en méthode Scrum.

---

## Product Backlog

| ID    | User Story                                                                                                                                      | Priorité | Story Points |
|-------|-------------------------------------------------------------------------------------------------------------------------------------------------|:--------:|:------------:|
| US01  | En tant qu’Hôtelier, je veux m’enregistrer pour accéder à la plateforme.                                                                         | Haute    |      3       |
| US02  | En tant qu’Hôtelier, je veux me connecter pour accéder à mon dashboard.                                                                          | Haute    |      3       |
| US03  | En tant que Client, je veux m’enregistrer pour pouvoir faire des réservations.                                                                   | Haute    |      3       |
| US04  | En tant que Client, je veux me connecter pour pouvoir réserver.                                                                                  | Haute    |      3       |
| US05  | En tant que Client, je veux consulter la liste des chambres disponibles pour des dates données.                                                   | Haute    |      5       |
| US06  | En tant que Client, je veux filtrer les chambres par type, prix et équipements.                                                                   | Moyenne  |      5       |
| US07  | En tant que Client, je veux effectuer une réservation et recevoir une confirmation.                                                              | Haute    |      8       |
| US08  | En tant que Client, je veux annuler une réservation et recevoir un remboursement.                                                                 | Moyenne  |      5       |
| US09  | En tant que Client, je veux laisser un avis après mon séjour.                                                                                     | Basse    |      3       |
| US10  | En tant qu’Hôtelier, je veux gérer les chambres (CRUD).                                                                                           | Haute    |      8       |
| US11  | En tant qu’Hôtelier, je veux visualiser les statistiques de réservation.                                                                           | Moyenne  |      5       |
| US12  | En tant que Système, je veux traiter les paiements via Stripe et PayPal.                                                                          | Haute    |      8       |
| US13  | En tant que Système, je veux envoyer des notifications par email et SMS.                                                                           | Moyenne  |      5       |
| US14  | En tant que Système, je veux vérifier la disponibilité des chambres.                                                                               | Haute    |      5       |
| US15  | En tant qu’Utilisateur, je veux récupérer mon mot de passe oublié.                                                                                 | Moyenne  |      3       |
| US16  | En tant que DevOps, je veux déployer les services via Docker et Kubernetes.                                                                        | Moyenne  |      8       |

---

## Sprint 1 (Semaine 1) – Authentification & Users

**Objectif** : Mettre en place l’infrastructure de sécurité et les services utilisateurs.

| Backlog Item | Tâches                                                                                         |
|--------------|------------------------------------------------------------------------------------------------|
| US01         | - createUserRegistrationEndpoint<br>- implementUserEntityEtRepo<br>- writeUserServiceTests    |
| US02         | - createLoginEndpoint<br>- configureJwtAuthFilter<br>- implementJwtTokenProvider              |
| US03         | - createClientRegistrationEndpoint (réutilise userService)<br>- adjustRoleAssignment          |
| US04         | - createClientLoginFlow (front + back)<br>- secureEndpointsWithJwt                            |
| US15         | - implementPasswordResetEndpoint<br>- configureEmailServiceForResetLink                       |

---

## Sprint 2 (Semaine 2) – Gestion des chambres & disponibilité

**Objectif** : Développer le micro‑service roomService et la recherche de chambres.

| Backlog Item | Tâches                                                                                                   |
|--------------|----------------------------------------------------------------------------------------------------------|
| US10         | - createRoomEntityEtRepo<br>- implementRoomServiceCRUD<br>- exposeRoomControllerEndpoints               |
| US14         | - implementAvailabilityCheck(roomId, dates)<br>- addCircuitBreakerResilience4j                          |
| US05         | - createGetAvailableRoomsEndpoint<br>- implementRoomDtoMapping                                          |
| US06         | - extendGetRoomsWithFilters(type, prix, équipements)<br>- writeRoomServiceTests                         |

---

## Sprint 3 (Semaine 3) – Réservations, paiements & notifications

**Objectif** : Mettre en place reservationService, paymentService et notificationService.

| Backlog Item | Tâches                                                                                                           |
|--------------|------------------------------------------------------------------------------------------------------------------|
| US07         | - createReservationEntityEtRepo<br>- implementCreateReservation<br>- writeReservationAvailabilityIntegrationTest |
| US08         | - implementCancelReservationFlow<br>- integrateRefundInPaymentService                                           |
| US12         | - configureStripeClient<br>- configurePayPalClient<br>- implementProcessPaymentInPaymentService                  |
| US13         | - configureTwilioConfig<br>- implementSendEmailViaSendGrid<br>- implementSendSmsViaTwilio                        |

---

## Sprint 4 (Semaine 4) – Avis, stats & DevOps

**Objectif** : Finaliser les fonctionnalités secondaires et préparer le déploiement.

| Backlog Item | Tâches                                                                                              |
|--------------|-----------------------------------------------------------------------------------------------------|
| US09         | - createReviewEntityEtRepo<br>- implementSubmitReview<br>- moderateReviewBeforePublish              |
| US11         | - implementStatsEndpoint(reservations, revenus)<br>- addDashboardStatsInFrontEnd                   |
| US16         | - writeDockerfilesPourChaqueService<br>- créer docker-compose.yml<br>- créer manifests Kubernetes  |
| **Buffer**   | - tests de bout en bout<br>- corrections de bugs<br>- revue de code et documentation                |

---

N’hésite pas à ajuster les priorités ou redistribuer les items entre sprints selon tes besoins !