# 🐾 MichiTienda - E-Commerce Fullstack (Eva 03)

Proyecto web e-commerce desarrollado con arquitectura fullstack dockerizada y desplegado en **Render** con base de datos PostgreSQL serverless en **Neon**.

## 🛠️ Stack Tecnológico
* **Backend:** Java + Spring Boot (Maven)
* **Frontend:** Node.js
* **Base de Datos:** PostgreSQL (NeonDB)
* **Contenedores & Despliegue:** Docker + Render (CI/CD automático desde `main`)

---

## 📁 Estructura del Monorepo

```text
MichiTienda-Eva03-Fullstack2/
├── backend/          # Proyecto Spring Boot (Maven) + Dockerfile
├── frontend/         # Proyecto Node.js + Dockerfile
├── .gitignore        # Archivos ignorados por Git
└── README.md         # Documentación y reglas del equipo
```

---

## ⚠️ Reglas Importantes para el Equipo

Para evitar conflictos en GitHub y errores en el despliegue automático de Render, sigue estas reglas:

1. **Respeta las carpetas:** Todo el código de Java/Spring Boot va dentro de `backend/` y todo el código de Node.js va dentro de `frontend/`. **No subas archivos sueltos en la raíz.**
2. **NO ejecutes `git init` dentro de `backend/` o `frontend/`:** El repositorio de Git es únicamente la carpeta principal.
3. **Trabaja siempre en tu propia rama:** No hagas *push* directo a `main`. Crea una rama antes de subir cambios:
   ```bash
   git checkout -b feat/nombre-de-tu-tarea
   ```
4. **Prohibido subir contraseñas o `.env`:** Nunca escribas las credenciales reales de Neon en `application.properties` ni subas archivos `.env`. Usa variables de entorno locales y configúralas en Render.
5. **No borres los `Dockerfile` ni `.dockerignore`:** Son necesarios para que Render construya y despliegue cada servicio automáticamente.

---

## 🚀 Cómo empezar a trabajar (Primeros pasos)

1. Clona este repositorio en tu computador:
   ```bash
   git clone <URL_DE_ESTE_REPOSITORIO>
   cd MichiTienda-Eva03-Fullstack2
   ```
2. *(Solo si usas un PC del laboratorio)* Configura tu identidad de Git sin `--global`:
   ```bash
   git config user.email "tu_correo_github@ejemplo.com"
   git config user.name "Tu Nombre"
   ```
3. Copia tus archivos dentro de `backend/` o `frontend/` (asegurándote de **no** copiar carpetas `.git`, `target/` ni `node_modules/`).
4. Crea tu rama, haz `commit` y sube tus cambios (`push`) para abrir un **Pull Request** hacia `main`.
