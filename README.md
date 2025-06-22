# 🎓 Sistema de Gestión Académica - Spring Boot

Mi proyecto de **Spring Boot** para la universidad. Es una aplicación web que maneja un sistema académico con usuarios, proyectos y tutorías.

## 📋 ¿Qué hace esta aplicación?

Es un sistema completo de gestión académica que permite:
- Gestionar usuarios (estudiantes, profesores)
- Crear y administrar proyectos académicos
- Organizar tutorías con inscripciones
- Visualizar contenido en formato de tarjetas estilo Pinterest
- Subir imágenes a Firebase Storage

## 🛠️ Tecnologías que usé

### **Backend**
- **Spring Boot** - El framework principal
- **Spring Data JPA** - Para conectar con la base de datos
- **PostgreSQL** - Base de datos (uso Supabase gratis)
- **Maven** - Para manejar las dependencias

### **Frontend**
- **Thymeleaf** - Para generar las páginas HTML
- **Bootstrap 5** - Para que se vea bonito y responsivo
- **JavaScript** - Para funcionalidades dinámicas
- **Firebase Storage** - Para almacenar imágenes

### **Características Adicionales**
- **Firebase SDK** - Integración para subida de archivos
- **Font Awesome** - Iconos modernos
- **AJAX** - Para carga dinámica de contenido

## ✨ Funcionalidades

### **Gestión de Usuarios**
- Agregar nuevos usuarios
- Editar información de usuarios
- Eliminar usuarios
- Validación de email único
- Gestión de contraseñas

### **Gestión de Proyectos**
- **Vista de Gestión**: CRUD completo para administradores
- **Vista de Tarjetas**: Visualización estilo Pinterest para usuarios
- Subida de imágenes a Firebase Storage
- Asociación con usuarios
- Modal de detalles con información completa
- Búsqueda y filtrado por autor

### **Gestión de Tutorías**
- **Vista de Gestión**: CRUD completo para administradores
- **Vista de Tarjetas**: Visualización estilo Pinterest para usuarios
- Selección múltiple de horarios (9am-8pm)
- Sistema de inscripción de asistentes
- Gestión de participantes
- Subida de imágenes a Firebase Storage
- Filtrado por tema y creador
- Horarios mostrados como chips expandibles

## 🗄️ Base de Datos

Tengo 4 tablas principales:

```
Usuario
├── id_usuario 
├── nombre
├── email (único)
└── password

Proyecto
├── id_proyecto
├── nombre
├── descripcion
├── imagen (URL de Firebase)
└── id_usuario (creador del proyecto)

Tutoria
├── id_tutoria
├── titulo
├── tema
├── horario (horarios disponibles)
├── imagen (URL de Firebase)
└── id_creado_por (usuario creador)

TutoriaAsistente
├── id
├── id_tutoria (tutoría)
└── id_usuario (asistente inscrito)
```

## 📚 Lo que aprendí haciendo este proyecto

### **Spring Boot Avanzado**
- Configuración de múltiples endpoints para diferentes vistas
- Manejo de relaciones complejas entre entidades
- Uso de @JsonManagedReference y @JsonBackReference
- Operaciones transaccionales complejas

### **Firebase Integration**
- Configuración del SDK de Firebase
- Subida de archivos con progress tracking
- Gestión de URLs de descarga
- Manejo de errores en uploads

### **Frontend Moderno**
- Diseño responsivo con Bootstrap 5
- Layout de tarjetas estilo Pinterest
- Modales dinámicos con datos
- Búsqueda y filtrado en tiempo real
- Chips para mostrar información
- Estados de carga y error

### **UX/UI Design**
- Navegación intuitiva con sidebar
- Separación clara entre vistas de gestión y públicas
- Feedback visual con alertas
- Previsualización de imágenes
- Estados vacíos informativos

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/cibertec/t2/
│   │   ├── T2Application.java           # Archivo principal
│   │   ├── controller/                  # Controladores
│   │   │   ├── HomeController.java
│   │   │   ├── UsuarioController.java
│   │   │   ├── ProyectoController.java
│   │   │   └── TutoriaController.java
│   │   ├── service/                     # Servicios (lógica de negocio)
│   │   │   ├── UsuarioService.java
│   │   │   ├── ProyectoService.java
│   │   │   └── TutoriaService.java
│   │   ├── repository/                  # Repositorios (conexión con BD)
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── ProyectoRepository.java
│   │   │   ├── TutoriaRepository.java
│   │   │   └── TutoriaAsistenteRepository.java
│   │   └── model/                       # Modelos (entidades JPA)
│   │       ├── Usuario.java
│   │       ├── Proyecto.java
│   │       ├── Tutoria.java
│   │       └── TutoriaAsistente.java
│   └── resources/
│       ├── templates/                   # Páginas HTML
│       │   ├── usuarios.html            # Gestión de usuarios
│       │   ├── proyectos.html           # Gestión de proyectos
│       │   ├── proyectos-cards.html     # Vista de tarjetas de proyectos
│       │   ├── tutorias.html            # Gestión de tutorías
│       │   ├── tutorias-cards.html      # Vista de tarjetas de tutorías
│       │   └── layout.html              # Layout base (legacy)
│       ├── firebase-service-account.json # Configuración Firebase
│       └── application.properties       # Configuración de la app
```

## 🚀 Cómo ejecutar el proyecto

### **Lo que necesitas tener instalado:**
- **Java 21** 
- **Maven**
- Un IDE como IntelliJ o VS Code

### **Pasos para ejecutar:**

1. **Clonar el proyecto**
   ```bash
   git clone <url-del-repositorio>
   cd t2
   ```

2. **Configurar Firebase** (opcional, para subida de imágenes)
   - Crear proyecto en Firebase Console
   - Actualizar configuración en las plantillas HTML

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **Abrir en el navegador**
   - Ir a: `http://localhost:8081`
   - Usar el menú lateral para navegar

## 🌐 Páginas disponibles

### **Vistas Públicas (Tarjetas)**
- `/proyectos` - Vista de tarjetas de proyectos estilo Pinterest
- `/tutorias` - Vista de tarjetas de tutorías con inscripción

### **Vistas de Gestión (Administración)**
- `/usuarios` - Gestión completa de usuarios
- `/proyectos/gestion` - Gestión completa de proyectos
- `/tutorias/gestion` - Gestión completa de tutorías

### **Funcionalidades AJAX**
- `/tutorias/{id}/asistentes` - Lista de asistentes de una tutoría
- `/proyectos/{id}/detalle` - Detalles completos de un proyecto

## 🔧 Problemas que tuve y cómo los solucioné

- **Referencias circulares JSON**: Usé @JsonManagedReference y @JsonBackReference
- **Subida de archivos**: Integré Firebase Storage con progress tracking
- **Múltiples vistas**: Separé endpoints para gestión y visualización pública
- **Selección de horarios**: Creé sistema de checkboxes con almacenamiento como string
- **Filtrado dinámico**: Implementé búsqueda en tiempo real con JavaScript
- **Duplicados en filtros**: Eliminé duplicados programáticamente en el frontend
- **Layout responsivo**: Usé CSS Grid/Flexbox para tarjetas adaptables

## 🎨 Características de Diseño

### **Vista de Tarjetas**
- Layout responsivo estilo Pinterest
- Imágenes optimizadas con object-fit
- Descripción truncada con "Ver más"
- Chips para horarios expandibles
- Búsqueda y filtros en tiempo real

### **Vista de Gestión**
- Tablas completas con todas las acciones CRUD
- Modales para formularios
- Subida de imágenes con preview
- Validación de formularios
- Mensajes de éxito/error

### **Navegación**
- Sidebar fijo con iconos
- Separación clara entre vistas públicas y de gestión
- Estados activos visuales
- Breadcrumbs implícitos

## 💭 Cosas que podría mejorar

- **Autenticación**: Sistema de login con roles
- **API REST**: Endpoints para aplicaciones móviles
- **Notificaciones**: Sistema de alertas por email
- **Comentarios**: Sistema de feedback en proyectos
- **Categorías**: Organización más avanzada
- **Dashboard**: Estadísticas y métricas
- **Búsqueda avanzada**: Filtros más complejos
- **Favoritos**: Sistema de marcadores

## 🔥 Características Destacadas

- **Dual View System**: Vistas públicas y de gestión separadas
- **Firebase Integration**: Subida de imágenes en la nube
- **Pinterest-style Layout**: Diseño moderno y atractivo
- **Real-time Search**: Búsqueda instantánea sin recargar
- **Mobile Responsive**: Funciona perfecto en móviles
- **Progressive Enhancement**: Funciona sin JavaScript básico

---

*Proyecto de gestión académica con Spring Boot, diseñado para aprender desarrollo web full-stack moderno* 🎓📚 