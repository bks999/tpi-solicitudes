# Sistema de Gestión de Solicitudes Académicas

## Descripción

Sistema de consola desarrollado en Java para gestionar solicitudes académicas de estudiantes.
Permite registrar solicitudes, administrar sus estados, asignar responsables y consultar información mediante búsquedas y reportes.

Los datos incluidos en el proyecto son ficticios y se utilizan únicamente con fines de prueba.

## Requisitos

- Java Development Kit (JDK) 21 o superior.
- IntelliJ IDEA (opcional, se puede utilizar cualquier entorno compatible con Java 21).

## Cómo ejecutar el proyecto

### Desde IntelliJ IDEA

1. Clonar o descargar el repositorio.
2. Abrir el proyecto desde IntelliJ IDEA o el entorno de desarrollo elegido.
3. Configurar el **Working directory** en la carpeta raíz del proyecto, donde se encuentran las carpetas `src/` y `data/`.
4. Verificar que el proyecto utilice JDK 21.
5. Ejecutar la clase `Main`.

### Desde la terminal

Desde la raíz del proyecto:

```bash
javac -d out src/*.java
java -cp out Main
```

**Nota:** en la versión inicial (tag `estado-inicial`), el directorio de trabajo debe ser la raíz del proyecto para que el sistema encuentre la carpeta `data/`. A partir de `etapa-refactoring`, el sistema localiza la carpeta automáticamente.

## Funcionalidades

El sistema presenta un menú principal con las siguientes opciones:

1. **Registrar solicitud:** permite crear una nueva solicitud académica asociada a un estudiante.
2. **Cambiar estado de solicitud:** permite actualizar el estado de una solicitud según las reglas del sistema.
3. **Asignar responsable:** permite asignar un responsable a una solicitud, considerando el área correspondiente.
4. **Buscar / filtrar solicitudes:** permite consultar solicitudes mediante distintos criterios de búsqueda.
5. **Generar reporte:** muestra información general y estadísticas sobre las solicitudes registradas.
6. **Listar todas las solicitudes:** permite visualizar las solicitudes almacenadas en el sistema.

La opción **0** finaliza la ejecución del programa.

## Estructura del proyecto

```text
tpi-solicitudes/
├── src/
│   ├── Main.java
│   ├── SistemaSolicitudes.java
│   ├── Solicitud.java
│   ├── DatosSolicitud.java
│   ├── Responsable.java
│   └── Estudiante.java
├── data/
│   ├── estudiantes.csv
│   ├── responsables.csv
│   └── solicitudes.csv
├── docs/
├── .gitignore
└── README.md
```

- **`src/`:** contiene las clases Java que implementan el sistema y sus funcionalidades.
- **`data/`:** contiene los archivos CSV utilizados para almacenar la información de estudiantes, responsables y solicitudes.
- **`docs/`:** reúne la documentación, el checklist de Code Review y las evidencias del proceso de análisis, pruebas y refactorización.

## Tags del repositorio

Los siguientes tags identifican las distintas etapas del desarrollo:

| Tag | Descripción |
|---|---|
| `estado-inicial` | Versión original del sistema, antes de comenzar el diagnóstico. |
| `etapa-diagnostico` | Versión con el análisis inicial y los casos de prueba ejecutados. |
| `etapa-refactoring` | Versión con los refactorings realizados. |
| `etapa-pruebas` | Versión con las pruebas posteriores a los refactorings y su comparación. |
| `etapa-docs` | Versión con la documentación y las evidencias incorporadas. |
| `estado-refactorizado` | Estado final del sistema luego de las modificaciones y