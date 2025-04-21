

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




Voici une proposition pour la partie frontend :
Product Backlog Frontend
ID	User Story	Priorité	Story Points
USF01	En tant qu’utilisateur, je veux m’enregistrer (signup) pour pouvoir accéder à la plateforme.	Haute	3
USF02	En tant qu’utilisateur, je veux me connecter (login) pour accéder à mon dashboard.	Haute	3
USF03	En tant que client, je veux voir la liste des chambres disponibles pour des dates données.	Haute	5
USF04	En tant que client, je veux filtrer la liste par type, prix et équipements.	Moyenne	5
USF05	En tant que client, je veux consulter le détail d’une chambre (photos, description, tarifs).	Moyenne	3
USF06	En tant que client, je veux remplir un formulaire de réservation et voir un récapitulatif avant validation.	Haute	8
USF07	En tant que client, je veux recevoir une confirmation de ma réservation.	Haute	3
USF08	En tant que client, je veux annuler une réservation depuis mon profil.	Moyenne	5
USF09	En tant que client, je veux laisser un avis et une note après mon séjour.	Basse	3
USF10	En tant qu’hôtelier, je veux un dashboard avec statistiques de réservations et revenus.	Moyenne	8
USF11	En tant que dev, je veux configurer le routage (React Router) et la gestion d’état (Context API ou Redux).	Haute	5
USF12	En tant que dev, je veux une CI/CD front (lint, tests, build, deploy Docker).	Moyenne	8
Sprint Backlogs (1 mois = 4 sprints de 1 semaine)
Sprint 1 (Semaine 1) – Authentification & routage

Objectif : Setup de l’app, pages signup/login, routage sécurisé.

    USF01, USF02, USF11

        init projet Vite + React + Tailwind

        créer routes /signup, /login, /dashboard

        développer composants signupForm, loginForm

        implémenter authService.js (axios, tokenStorage)

        protéger routes privées avec authContext

Sprint 2 (Semaine 2) – Liste et filtrage des chambres

Objectif : Affichage et filtrage des chambres.

    USF03, USF04, USF05

        créer page roomsPage.jsx

        développer composants roomCard, roomFilter

        implémenter roomService.js (GET /rooms?from=&to=&type=&…)

        gérer loading/error states et tests unitaires

Sprint 3 (Semaine 3) – Réservation & confirmation

Objectif : Formulaire de réservation et confirmation.

    USF06, USF07, USF08

        page reservationPage.jsx avec reservationForm

        appels à reservationService.js et paymentService.js

        notifications in‑app (toasts) et redirection vers confirmationPage.jsx

        implémenter annulation depuis profilePage.jsx

Sprint 4 (Semaine 4) – Avis, stats & CI/CD

Objectif : Avis utilisateurs, dashboard hôtelier et pipeline front.

    USF09, USF10, USF12

        page reviewPage.jsx et composant reviewForm

        page dashboardPage.jsx avec graphiques simples (recharts)

        config GitHub Actions / GitLab CI : lint, tests (Jest/RTL), build, Dockerfile

        tests E2E rapides (Cypress)

Buffer (en cas de retard) : corrections de bugs, refinements UX/UI, accessibilité.
