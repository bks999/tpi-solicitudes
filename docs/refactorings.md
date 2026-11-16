# Refactorings aplicados y decisiones técnicas

## 1. Objetivo

Se realizaron mejoras sobre el Sistema de Gestión de Solicitudes Académicas con el propósito de reducir la deuda técnica identificada durante el diagnóstico, mejorar la legibilidad del código y facilitar su mantenimiento.

Los cambios se desarrollaron en la rama `feature/refactoring`, tomando como punto de partida la versión identificada mediante el tag `etapa-diagnostico`.

## 2. Aplicación de SOLID y Clean Code

### Principio de responsabilidad única (SRP)

Se mejoró la separación de responsabilidades mediante la extracción de métodos específicos para seleccionar y validar el tipo y la prioridad de las solicitudes.

Además, se trasladó la operación de creación de solicitudes desde `SistemaSolicitudes` hacia la clase `Solicitud`.

Estas modificaciones reducen parcialmente la concentración de responsabilidades identificada en el problema 3.1 (God Class).

### Clean Code

Se aplicaron las siguientes mejoras:

- Extracción de métodos para reducir la extensión de `registrarSolicitud()`.
- Sustitución de nombres de variables poco descriptivos por identificadores más claros.
- Agrupación de los datos de creación mediante `DatosSolicitud`.
- Validación explícita de entradas incorrectas.
- Corrección del cálculo del promedio de prioridades.
- Centralización de la resolución de rutas de los archivos CSV.

Estas modificaciones mejoran la comprensión del código y reducen algunos riesgos de mantenimiento.

No se considera que los cinco principios SOLID hayan quedado implementados completamente, ya que el alcance de las modificaciones se concentró principalmente en la separación de responsabilidades y la legibilidad.

## 3. Técnicas de refactoring aplicadas

### 3.1. Extract Method

**Problema diagnosticado:** 3.2 — Long Method.

**Ubicación:** `SistemaSolicitudes.java`, método `registrarSolicitud()`.

**Situación inicial:** el método concentraba distintas operaciones, incluyendo la presentación de opciones, lectura de entradas y validación de datos.

**Modificación realizada:** se extrajeron los métodos `seleccionarTipoSolicitud()` y `seleccionarPrioridad()`.

**Resultado:** el método principal delega estas operaciones a métodos auxiliares con nombres descriptivos. Se verificó que el registro de solicitudes continuara funcionando.

### 3.2. Introduce Parameter Object

**Problema diagnosticado:** 3.10 — Long Parameter List.

**Ubicación:** método `crearSolicitud()`.

**Situación inicial:** el método recibía nueve parámetros individuales para crear una solicitud.

**Modificación realizada:** se creó la clase `DatosSolicitud`, que agrupa los valores necesarios para construir una solicitud. Se modificó `crearSolicitud()` para recibir dicho objeto.

**Resultado:** se simplificó la firma del método y se hizo más explícita la agrupación de los datos. El constructor de `DatosSolicitud` continúa recibiendo nueve argumentos, por lo que la simplificación es parcial.

### 3.3. Move Method

**Problema diagnosticado:** 3.1 — God Class.

**Ubicación:** `SistemaSolicitudes.java` y `Solicitud.java`.

**Situación inicial:** la operación `crearSolicitud()` se encontraba dentro de la clase principal del sistema.

**Modificación realizada:** se trasladó el método a `Solicitud`, donde funciona como método estático de creación.

**Resultado:** se reubicó una responsabilidad relacionada con la construcción de solicitudes. El cambio representa una mejora puntual y no elimina por completo la concentración de responsabilidades de `SistemaSolicitudes`.

### 3.4. Rename Variable

**Problema diagnosticado:** 3.11 — Poor Naming.

**Ubicación:** método `registrarSolicitud()`.

**Situación inicial:** se utilizaban variables abreviadas como `t`, `p`, `d` y `leg`.

**Modificación realizada:** se reemplazaron por `tipoSolicitud`, `prioridad`, `descripcion` y `legajo`.

**Resultado:** los identificadores expresan con mayor claridad su función y facilitan la lectura y el mantenimiento del código.

## 4. Corrección de problemas funcionales

### 4.1. Manejo de entradas inválidas — CP-07

Se incorporó el tratamiento de `NumberFormatException` en la búsqueda por prioridad.

Antes, ingresar texto no numérico podía finalizar inesperadamente el programa. Después de la corrección, se muestra un mensaje de error y se regresa al menú.

### 4.2. Cálculo del promedio — CP-08

Se corrigió la división entera en `reporte()` mediante una conversión a `double` y una comprobación para evitar divisiones por cero.

Con los datos originales, el promedio pasó de `1.0` a `1.875`.

### 4.3. Resolución de rutas — CP-09

Se incorporó `obtenerCarpetaDatos()` para localizar la carpeta de archivos CSV a partir de la ubicación del programa compilado.

También se actualizaron las rutas de lectura y escritura. Se verificó que el sistema pudiera cargar solicitudes y generar el reporte desde un directorio diferente al del proyecto.

### 4.4. Validación de responsables — CP-12

Se corrigió el tratamiento de entradas no numéricas al buscar solicitudes por responsable.

Antes, una entrada inválida podía interpretarse como el ID `0`. Ahora, el sistema informa el error y evita realizar una búsqueda incorrecta.

## 5. Verificación

Se ejecutaron los doce casos de prueba definidos durante el diagnóstico, CP-01 a CP-12, sobre la versión modificada.

Según las verificaciones manuales realizadas, las funcionalidades principales continuaron operando y los cuatro casos identificados inicialmente como fallidos presentaron el comportamiento corregido.

Las capturas de las pruebas posteriores se encuentran en `docs/evidencias/despues/`.

## 6. Limitaciones y mejoras pendientes

Si bien se resolvieron distintos problemas de legibilidad, validación y comportamiento, todavía existen aspectos que podrían mejorarse:

- Separar la lógica de persistencia CSV en una clase independiente.
- Mejorar el encapsulamiento de los atributos públicos.
- Reducir todavía más las responsabilidades de `SistemaSolicitudes`.
- Sustituir valores primitivos del dominio por tipos más específicos.
- Incorporar pruebas automatizadas que permitan detectar regresiones de manera repetible.

Estas mejoras quedan planteadas como posibles líneas de evolución del sistema.