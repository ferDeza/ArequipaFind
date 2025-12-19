Aquí tienes el código fuente en formato Markdown para que simplemente lo copies y pegues en tu archivo README.md.

Markdown

# Constru-App

**Constru-App** es una solución móvil integral desarrollada en Android y diseñada para conectar a profesionales de la construcción, técnicos y clientes. La aplicación permite gestionar perfiles profesionales, publicar ofertas de trabajo especializadas y facilitar la búsqueda de servicios técnicos mediante un sistema confiable de reseñas.

## Características Principales

* **Bolsa de Trabajo Especializada:** Sección para crear, visualizar y postularse a proyectos de construcción y mantenimiento técnico.
* **Gestión de Proyectos y Eventos:** Visualización de hitos, capacitaciones o eventos relevantes para el sector.
* **Sistema de Reseñas y Reputación:** Los usuarios pueden calificar la calidad del trabajo y el cumplimiento de los profesionales para generar confianza en la comunidad.
* **Autenticación Segura:** Acceso mediante correo electrónico e integración con Google Login a través de Firebase Auth.
* **Perfiles Profesionales:** Gestión de perfiles donde se verifica el estado de los trabajadores, sus habilidades y datos de contacto.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Interfaz moderna, reactiva y declarativa).
* **Backend & Base de Datos:** [Firebase](https://firebase.google.com/) (Cloud Firestore para persistencia en tiempo real y Firebase Auth para seguridad).
* **Inyección de Dependencias:** [Koin](https://insert-koin.io/).
* **Arquitectura:** Clean Architecture con el patrón **MVVM** (Model-View-ViewModel), asegurando un código escalable, testeable y fácil de mantener.
* **Navegación:** Jetpack Navigation Component.

## 📂 Estructura del Proyecto

El código sigue una estructura de capas para una clara separación de responsabilidades:

* **`core/`**: Configuración global, navegación centralizada y módulos compartidos.
* **`data/`**: Implementación de repositorios y fuentes de datos (Firebase Remote Data Sources).
* **`domain/`**: Modelos de datos de negocio, interfaces de repositorio y Casos de Uso (Use Cases).
* **`feature/`**: Módulos divididos por funcionalidades (Jobs, Login, Profile, Review, Home), cada uno con su lógica de UI y ViewModels.
* **`ui/theme/`**: Definición de la identidad visual (colores, tipografía y temas de Compose).

## ⚙️ Instalación y Configuración

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/ferdeza/arequipafind.git](https://github.com/ferdeza/arequipafind.git)
    ```
2.  **Configurar Firebase:**
    * Crea un nuevo proyecto en [Firebase Console](https://console.firebase.google.com/).
    * Registra una aplicación Android con el nombre de paquete `com.gamecodeschool.arequipafind`.
    * Descarga el archivo `google-services.json` y colócalo en la carpeta `app/`.
3.  **Compilación:**
    * Abre el proyecto en **Android Studio**.
    * Sincroniza los archivos de Gradle.
    * Ejecuta la aplicación en un dispositivo físico o emulador con API 24 o superior.

---
> **Nota:** Este proyecto evolucionó de *ArequipaFind* para convertirse en **Constru-App**
