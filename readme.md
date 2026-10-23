# 🧮 AppCalculadora

Aplicación móvil de calculadora para Android desarrollada como proyecto académico para la **Universidad Autónoma Juan Misael Saracho (UAJMS)**. 

El proyecto destaca por su proceso de migración técnica desde **Jetpack Compose** hacia **AppCompat** (vistas XML clásicas), solucionando conflictos de dependencias, reduciendo el tamaño del APK y mejorando la compatibilidad retroactiva.

---

## 📋 Tabla de Contenidos

* [Características](#-características)
* [Tecnologías Utilizadas](#-tecnologías-utilizadas)
* [Contexto y Migración Técnica](#-contexto-y-migración-técnica)
* [Estructura del Proyecto](#-estructura-del-proyecto)
* [Lógica y Arquitectura](#-lógica-y-arquitectura)
* [Instalación y Requisitos](#-instalación-y-requisitos)
* [Guía de Uso](#-guía-de-uso)
* [Documentación Adicional](#-documentación-adicional)
* [Contexto Académico y Autor](#-contexto-académico)

---

## ✨ Características

* **Operaciones básicas:** Suma, resta, multiplicación y división con soporte para números decimales.
* **Pantalla dual dinámica:** Visualización simultánea de la operación en curso (`tvOperation`) y del resultado o valor actual (`tvResult`).
* **Función de reinicio completo (*Clear All*):** Restaura el estado de la calculadora a sus valores iniciales.
* **Diseño personalizado:** Botones estilizados mediante recursos *drawable* dedicados (`btn_number`, `btn_operator`, `btn_equals`).
* **Alta compatibilidad:** Compatible con dispositivos desde Android 4.0 (API 14+).

---

## 🧰 Tecnologías Utilizadas

| Componente | Tecnología / Detalle |
| :--- | :--- |
| **Plataforma** | Android (minSdk: API 14 / targetSdk actual) |
| **Lenguaje** | Kotlin |
| **Interfaz de Usuario** | XML Layouts con vistas tradicionales (`AppCompatActivity`) |
| **Sistema de compilación** | Gradle con Kotlin DSL (`build.gradle.kts`) |
| **IDE** | Android Studio 2024+ |
| **Librerías principales** | `androidx.appcompat:appcompat:1.6.1`, `com.google.android.material:material:1.12.0` |

---

## 🔄 Contexto y Migración Técnica

### Problema Original
El proyecto se generó inicialmente con la plantilla estándar de **Jetpack Compose** de Android Studio. Al intentar crear una calculadora basada en layouts XML tradicionales, surgieron conflictos severos de dependencias e importación (por ejemplo: `import androidx.compose.ui.graphics.Color no encontrado`).

### Solución Implementada
Se retiró por completo la capa de Compose para adoptar de lleno el stack clásico de **AppCompat**:
1. **Limpieza de dependencias:** Se eliminaron las librerías `androidx.compose.*` en `build.gradle.kts` e incorporaron las dependencias esenciales de AppCompat y Material Design.
2. **Eliminación de archivos redundantes:** Se borraron `Color.kt`, `Theme.kt` y `Type.kt`.
3. **Estandarización XML:** Se configuraron `inicio.xml`, `themes.xml` y la lógica de vinculación directa mediante `findViewById`.

### Comparativa de Métricas

| Métrica / Aspecto | Jetpack Compose (Inicial) | AppCompat (Implementado) |
| :--- | :--- | :--- |
| **Archivos de UI/Código** | 4 archivos Kotlin | 1 Kotlin (`MainActivity`) + 1 XML (`inicio.xml`) |
| **Dependencias externas** | 8 dependencias Compose | 3 dependencias estándar AppCompat |
| **Tamaño final del APK** | ~2 MB mayor | **~2 MB menor** |
| **Compatibilidad mínima** | Android 5.0 (API 21+) | **Android 4.0 (API 14+)** |
| **Curva de mantenimiento** | Alta en proyectos simples | Clara separación Vista (XML) / Lógica (Kotlin) |

---

## 📁 Estructura del Proyecto

```text
AppCalculadora/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/appcalculadora/
│           │   └── MainActivity.kt          # Lógica principal, estado y listeners
│           └── res/
│               ├── drawable/
│               │   ├── btn_number.xml       # Estilo para botones numéricos
│               │   ├── btn_operator.xml     # Estilo para botones de operadores
│               │   └── btn_equals.xml       # Estilo para el botón igual
│               ├── layout/
│               │   └── inicio.xml           # Layout principal de la interfaz
│               └── values/
│                   ├── colors.xml           # Paleta de colores
│                   ├── strings.xml          # Textos de la aplicación
│                   └── themes.xml           # Tema base AppCompat
├── gradle/wrapper/                          # Wrapper de Gradle
├── build.gradle.kts                         # Configuración raíz de Gradle
├── settings.gradle.kts                      # Declaración de módulos
├── gradle.properties                        # Propiedades de compilación
└── documentacion.pdf                        # Documentación técnica completa
```

---

## ⚙️ Lógica y Arquitectura

### Flujo de Ejecución

```text
[Usuario presiona un botón]
         ↓
[MainActivity detecta el evento (OnClickListener)]
         ↓
[Ejecución de la función correspondiente]
         ↓
[Actualización de variables internas (firstNumber, operation, currentNumber)]
         ↓
[Actualización en pantalla de TextViews (tvResult / tvOperation)]
```

### Funciones Principales (`MainActivity.kt`)

* `appendNumber(digit: String)`: Concatena los dígitos al operando actual en pantalla.
* `setOperation(op: String)`: Almacena la operación matemática elegida y preserva el primer número.
* `calculate()`: Evalúa la operación entre los dos valores y muestra el resultado final.
* `clearAll()`: Restablece las variables de estado y limpia las vistas numéricas.

---

## 🚀 Instalación y Requisitos

### Requisitos Previos
* Android Studio (versión 2024 o superior recomendada).
* JDK 17 o compatible con la versión de Android Gradle Plugin.
* Emulador Android o dispositivo físico conectado con depuración USB (API 14 o superior).

### Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Yoel-Cristian-Quispe-Diaz/AppCalculadora.git
   ```
2. Abre Android Studio y selecciona **File > Open**, navegando a la carpeta clonada.
3. Espera a que Gradle termine de sincronizar las dependencias.
4. Presiona el botón **Run** (▶️ o `Shift + F10`) seleccionando tu emulador o dispositivo de prueba.

---

## 📖 Guía de Uso

1. **Ingreso numérico:** Toca los botones del teclado (0 al 9) para ingresar el primer valor.
2. **Selección de operador:** Presiona el operador aritmético deseado (`+`, `-`, `*`, `/`). La operación se reflejará en la parte superior.
3. **Segundo número y cálculo:** Introduce el segundo valor y presiona el botón `=` para ver el resultado en pantalla.
4. **Limpiar pantalla:** Presiona el botón de borrado total (`C` / *Clear*) para restablecer el cálculo.

---

## 📚 Documentación Adicional

Para profundizar en el análisis comparativo, el proceso de resolución de errores y el código detallado, consulta el archivo [documentacion.pdf](documentacion.pdf) incluido en la raíz de este repositorio.

---

## 👤 Autor

* **Yoel Cristian Quispe Diaz** — [@Yoel-Cristian-Quispe-Diaz](https://github.com/Yoel-Cristian-Quispe-Diaz)
