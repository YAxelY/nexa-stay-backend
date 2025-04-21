hotel-management-frontend/
├── public/
│   ├── index.html
│   └── favicon.ico
├── src/
│   ├── assets/                  # images, polices…
│   ├── components/
│   │   ├── auth/
│   │   │   ├── loginForm.jsx
│   │   │   └── signupForm.jsx
│   │   ├── rooms/
│   │   │   ├── roomCard.jsx
│   │   │   └── roomFilter.jsx
│   │   ├── reservation/
│   │   │   ├── reservationForm.jsx
│   │   │   └── confirmation.jsx
│   │   ├── review/
│   │   │   └── reviewForm.jsx
│   │   └── common/
│   │       ├── header.jsx
│   │       ├── footer.jsx
│   │       └── toast.jsx
│   ├── context/
│   │   └── authContext.js
│   ├── hooks/
│   │   └── useFetch.js
│   ├── pages/
│   │   ├── loginPage.jsx
│   │   ├── signupPage.jsx
│   │   ├── roomsPage.jsx
│   │   ├── reservationPage.jsx
│   │   ├── confirmationPage.jsx
│   │   ├── profilePage.jsx
│   │   └── dashboardPage.jsx
│   ├── services/
│   │   ├── authService.js
│   │   ├── roomService.js
│   │   ├── reservationService.js
│   │   ├── paymentService.js
│   │   └── reviewService.js
│   ├── utils/
│   │   ├── formatDate.js
│   │   └── validators.js
│   ├── App.jsx
│   ├── index.jsx
│   └── styles/
│       └── tailwind.css
├── .eslintrc.js
├── .prettierrc
├── package.json
├── tailwind.config.js
├── postcss.config.js
└── vite.config.js
