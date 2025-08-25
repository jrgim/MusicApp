# MusicApp 🎵

Una aplicación Android para gestionar una biblioteca musical con funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar) y conectividad a API REST.

## 📱 Descripción

MusicApp es una aplicación Android nativa desarrollada en Kotlin que permite a los usuarios gestionar su biblioteca musical personal. La aplicación se conecta a una API REST para sincronizar datos y ofrece funcionalidades como búsqueda, favoritos, y gestión completa de canciones.

## ✨ Características

### Gestión de Canciones
- **Visualizar** lista de canciones con información detallada
- **Buscar** canciones por título
- **Agregar** nuevas canciones a la biblioteca
- **Editar** información de canciones existentes
- **Marcar** canciones como favoritas
- **Ver detalles** completos de cada canción

### Datos de Canciones
Cada canción incluye:
- Título
- Álbum
- Año de lanzamiento
- Duración (en segundos)
- Calificación (rating)
- Número de votos
- Artistas/Cantantes asociados
- Géneros musicales
- Estado de favorito

### Funcionalidades Adicionales
- **Conectividad de red** con verificación de API
- **Cache local** para funcionamiento offline
- **Pantalla de contacto** con opciones de llamada, email y SMS
- **Interfaz intuitiva** con Material Design
- **Modo favoritos** para filtrar canciones preferidas

## 🏗️ Arquitectura Técnica

### Tecnologías Utilizadas
- **Lenguaje**: Kotlin
- **Plataforma**: Android (SDK mínimo 31, objetivo 35)
- **UI**: View Binding + Material Design
- **Concurrencia**: Kotlin Coroutines
- **Networking**: HttpURLConnection con JSON
- **Arquitectura**: MVC con cache local

### Estructura del Proyecto
```
app/src/main/java/es/usj/jglopez/individualtask/
├── activities/          # Actividades de la aplicación
│   ├── Splash.kt       # Pantalla de carga inicial
│   ├── MainActivity.kt # Lista principal de canciones
│   ├── DetailActivity.kt # Detalles de canción
│   ├── AddEditSongActivity.kt # Agregar/editar canciones
│   └── Contact.kt      # Pantalla de contacto
├── model/              # Modelos de datos
│   ├── Song.kt        # Entidad canción
│   ├── Singer.kt      # Entidad cantante
│   ├── Genre.kt       # Entidad género
│   └── *Cache.kt      # Sistema de cache local
├── network/            # Conectividad de red
│   └── ApiHelper.kt   # Funciones de API REST
└── adapters/          # Adaptadores para RecyclerView
```

## 🌐 API REST

La aplicación se conecta a una API REST que debe estar ejecutándose en:
```
http://10.0.2.2:8080
```

### Endpoints Requeridos

#### Canciones
- `GET /songs` - Obtener todas las canciones
- `POST /songs` - Crear nueva canción
- `PUT /songs` - Actualizar canción existente

#### Cantantes
- `GET /singers` - Obtener todos los cantantes

#### Géneros
- `GET /genres` - Obtener todos los géneros

### Formato JSON de Canción
```json
{
  "id": 1,
  "title": "Nombre de la canción",
  "album": "Nombre del álbum",
  "year": 2024,
  "runtime": 240,
  "rating": 8,
  "votes": 1500,
  "singers": [1, 2],
  "genres": [1]
}
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- **Android Studio**: Arctic Fox o superior
- **JDK**: OpenJDK 11 o superior
- **Android SDK**: Nivel 31 o superior (Android 12)
- **Gradle**: 8.11.1 (incluido con Android Studio)
- **AGP**: Android Gradle Plugin 8.9.1
- **Kotlin**: 2.0.21
- **Dispositivo/Emulador**: Android API 31+ (Android 12)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/jrgim/MusicApp.git
   cd MusicApp
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega hasta la carpeta del proyecto

3. **Configurar la API**
   - Asegúrate de que tu API REST esté ejecutándose en `http://10.0.2.2:8080`
   - Para dispositivo físico, cambia la IP en `ApiHelper.kt` por la IP de tu servidor

4. **Sincronizar proyecto**
   - Android Studio sincronizará automáticamente las dependencias
   - Si no, ejecuta `Sync Project with Gradle Files`

5. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Presiona el botón "Run" o usa `Shift + F10`

## 📱 Uso de la Aplicación

### Primera Ejecución
1. La app iniciará con una **pantalla de splash** que verificará la conectividad con la API
2. Si la API está disponible, cargará datos de canciones, cantantes y géneros
3. Navegará automáticamente a la pantalla principal

### Pantalla Principal
- **Lista de canciones**: Muestra todas las canciones disponibles
- **Barra de búsqueda**: Busca canciones por título
- **Botón Favoritos**: Alterna entre mostrar todas las canciones o solo favoritas
- **Botón Agregar**: Permite agregar nuevas canciones
- **Botón Contacto**: Accede a las opciones de contacto

### Gestionar Canciones
- **Ver detalles**: Toca una canción para ver información completa
- **Editar**: Mantén presionada una canción para editarla
- **Agregar nueva**: Usa el botón "+" para agregar canciones
- **Marcar favorito**: Disponible desde la pantalla de detalles

### Pantalla de Contacto
- **Llamar**: +34 111 111 111
- **Email**: alu.139992@usj.es
- **SMS**: Enviar mensaje de texto

## 🔧 Configuración de Desarrollo

### Dependencias Principales
```kotlin
// Versiones especificadas en libs.versions.toml
androidx-core-ktx = "1.16.0"           // Core Android KTX
androidx-appcompat = "1.7.0"           // AppCompat
material = "1.12.0"                    // Material Design
androidx-activity = "1.10.1"           // Activity KTX
androidx-constraintlayout = "2.2.1"    // ConstraintLayout

// Testing
junit = "4.13.2"                       // Unit tests
androidx-junit = "1.2.1"               // Android tests
androidx-espresso-core = "3.6.1"       // UI tests
```

### Permisos Requeridos
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Configuración de Red
La aplicación incluye configuración de seguridad de red para permitir tráfico HTTP en desarrollo.

## 🐛 Solución de Problemas

### Error "API server isn't available"
- Verifica que la API REST esté ejecutándose
- Confirma que la URL `http://10.0.2.2:8080` sea accesible
- Para dispositivo físico, cambia la IP por la IP real del servidor

### Problemas de Conectividad
- Asegúrate de tener permisos de INTERNET
- Verifica la configuración de red del emulador/dispositivo
- Revisa los logs de Android para más detalles

### Errores de Compilación
- Verifica que tengas **Android Studio** con Android SDK instalado
- Asegúrate de tener **JDK 11** configurado en Android Studio
- Sincroniza el proyecto con Gradle (`File > Sync Project with Gradle Files`)
- Limpia y reconstruye el proyecto (`Build > Clean Project` > `Build > Rebuild Project`)
- Verifica que tengas las herramientas de SDK necesarias instaladas

### Ejecutar Tests
```bash
# Tests unitarios
./gradlew test

# Tests instrumentados (requiere dispositivo/emulador)
./gradlew connectedAndroidTest
```

## 👨‍💻 Desarrollo

### Estructura de Datos
La aplicación utiliza un sistema de cache local para mejorar el rendimiento:
- `SongCache`: Almacena canciones
- `SingerCache`: Almacena cantantes  
- `GenreCache`: Almacena géneros

### Flujo de Datos
1. **Splash Screen**: Carga datos iniciales desde la API
2. **Cache Local**: Almacena datos para acceso rápido
3. **Sincronización**: Actualiza cache después de modificaciones
4. **UI Updates**: Interfaz se actualiza automáticamente

## 📄 Licencia

Este proyecto es parte de una tarea individual académica para la Universidad San Jorge (USJ).

## 📞 Contacto

- **Email**: alu.139992@usj.es
- **Teléfono**: +34 111 111 111

---

**Nota**: Esta aplicación está diseñada para propósitos educativos y como demostración de desarrollo Android con Kotlin.