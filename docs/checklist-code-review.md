# Checklist de Code Review

Sistema de Gestión de Solicitudes Académicas · TPI Metodología de Sistemas II

## Objetivo

Evaluar la calidad del código en la versión inicial (tag `estado-inicial`) y en la versión refactorizada (rama `feature/refactoring`), verificando si las modificaciones mejoran la organización, la legibilidad y la mantenibilidad del sistema sin alterar su comportamiento esperado.

La revisión de la versión refactorizada se realizó sobre el Pull Request #1, con comentarios en el código y solicitud de cambios antes de la integración a `main`. Las correcciones se verificaron ejecutando nuevamente los casos de prueba antes de aprobar el Pull Request.

**Resultados posibles:** Cumple · No cumple · No aplica.

## Ítems del checklist

### 1. Diseño y organización

| Ítem | Criterio | Smell relacionado |
|---|---|---|
| CR-1.1 | Cada clase tiene responsabilidades identificables y no concentra funciones de distintas áreas del sistema. | 3.1 God Class |
| CR-1.2 | La lógica de negocio, la interacción con el usuario y el acceso a los archivos se encuentran separados. | 3.4 Tight Coupling |
| CR-1.3 | Cada método realiza una tarea principal y tiene una extensión de 30 líneas como máximo, salvo justificación. | 3.2 Long Method |
| CR-1.4 | Las clases dependen únicamente de los componentes necesarios para cumplir sus responsabilidades. | — |

### 2. Legibilidad y claridad

| Ítem | Criterio | Smell relacionado |
|---|---|---|
| CR-2.1 | Clases, métodos y variables tienen nombres claros, sin abreviaturas ni nombres genéricos (`aux`, `lista2`, `x`). | 3.11 Poor Naming |
| CR-2.2 | Los métodos mantienen un nivel de abstracción coherente, sin mezclar operaciones de alto nivel con detalles de implementación. | — |
| CR-2.3 | No existen bloques de código repetidos que puedan resolverse con métodos compartidos. | — |
| CR-2.4 | No existen variables, métodos o fragmentos comentados sin finalidad en el sistema. | 3.8 Dead Code |

### 3. Encapsulamiento y representación del dominio

| Ítem | Criterio | Smell relacionado |
|---|---|---|
| CR-3.1 | Los atributos tienen modificadores de acceso adecuados y no se modifican directamente desde otras clases. | 3.3 Public Fields |
| CR-3.2 | Los datos se validan antes de registrar o modificar información, evitando estados inválidos. | — |
| CR-3.3 | Los métodos reciben como máximo cuatro parámetros; si necesitan más, se agrupan en un objeto. | 3.10 Long Parameter List |
| CR-3.4 | Estados, tipos de solicitud y prioridades se definen mediante `enum` o constantes, sin cadenas ni números sueltos. | 3.5 Primitive Obsession |

### 4. Manejo de errores

| Ítem | Criterio | Smell relacionado |
|---|---|---|
| CR-4.1 | Las entradas del usuario se validan y los valores incorrectos no provocan interrupciones inesperadas. | 3.7 Poor Error Handling |
| CR-4.2 | Las excepciones se capturan solo cuando corresponde y no existen bloques `catch` vacíos. | — |
| CR-4.3 | Los mensajes de error informan claramente qué ocurrió y, cuando corresponde, cómo corregirlo. | — |

### 5. Pruebas y verificación

| Ítem | Criterio |
|---|---|
| CR-5.1 | Existen casos de prueba para las funcionalidades principales y las reglas de negocio. |
| CR-5.2 | Los casos de prueba se ejecutaron sobre la versión inicial y la versión refactorizada. |
| CR-5.3 | Los resultados de ambas versiones se compararon para verificar que el comportamiento esperado se mantuviera y que los defectos identificados se corrigieran (relacionado con 3.9, división entera). |
| CR-5.4 | Se conservaron evidencias de las pruebas en ambas versiones. |

### 6. Repositorio y documentación

| Ítem | Criterio |
|---|---|
| CR-6.1 | Los commits tienen mensajes claros que permiten identificar los cambios. |
| CR-6.2 | Existen los tags correspondientes a cada etapa del trabajo. |
| CR-6.3 | El README contiene la información necesaria para comprender y ejecutar el proyecto. |
| CR-6.4 | La carpeta `/docs` contiene la documentación y las evidencias requeridas. |
| CR-6.5 | No se incluyen archivos generados (`out/`, `.class`); el `.gitignore` los contempla. |

### 7. Consistencia y mantenimiento

| Ítem | Criterio | Smell relacionado |
|---|---|---|
| CR-7.1 | No existen métodos incompletos ni comentarios `TODO` pendientes. | 3.6 Implementación incompleta |
| CR-7.2 | Las modificaciones mantienen una estructura coherente con el resto del proyecto y no incorporan código innecesario. | — |
| CR-7.3 | Los cambios facilitan la comprensión y el mantenimiento del sistema. | — |

## Criterio de aceptación técnica

El código se considera aceptado cuando todos los ítems de las categorías 1 a 4 resultan **Cumple**, o cuando un incumplimiento está justificado y documentado como mejora pendiente. Las categorías 5 a 7 verifican las pruebas, la documentación y las condiciones generales de entrega.

## Registro de la revisión

| Ítem | Versión inicial | Versión refactorizada | Tras la corrección | Observación |
|---|---|---|---|---|
| CR-1.1 | No cumple | No cumple | Pendiente | `SistemaSolicitudes` sigue concentrando menú, lógica, búsquedas, reportes y persistencia. |
| CR-1.2 | No cumple | No cumple | Pendiente | La persistencia sigue en la misma clase; `registrarSolicitud()` combina consola, validación y creación. |
| CR-1.3 | No cumple | No cumple | Pendiente | `registrarSolicitud()` pasó de 90 a 76 líneas; `asignarResponsable()`, `reporte()` y `cambiarEstado()` siguen superando el máximo. |
| CR-1.4 | No cumple | No cumple | Pendiente | Las listas siguen siendo `public static`. |
| CR-2.1 | No cumple | No cumple | Pendiente | Renombres parciales en `registrarSolicitud()` (`35ba4e5`); siguen `lista2`, `resp`, `aux()`. |
| CR-2.2 | No cumple | No cumple | Pendiente | `reporte()` sigue calculando, formateando y escribiendo el archivo. |
| CR-2.3 | No cumple | No cumple | Pendiente | Siguen repetidas las ramas de `buscar()`, la búsqueda por ID y la lectura de números. |
| CR-2.4 | No cumple | No cumple | Cumple | Se eliminaron `flag` y el método comentado `eliminarSolicitud()` (`a5f1a28`). |
| CR-3.1 | No cumple | No cumple | Pendiente | Atributos públicos en `Solicitud` y `Responsable`. |
| CR-3.2 | No cumple | No cumple | Pendiente | CP-13 y CP-14 siguen fallando (pérdida de datos). |
| CR-3.3 | No cumple | Cumple, con observación | — | `crearSolicitud()` recibe `DatosSolicitud` (`29684fb`); los constructores siguen con 9 parámetros. |
| CR-3.4 | No cumple | No cumple | No cumple (mejora parcial) | Se reemplazó el `0` por la constante `PRIORIDAD_INVALIDA` (`e046a20`); estados y tipos siguen como texto. |
| CR-4.1 | No cumple | Cumple | — | CP-07 y CP-12 corregidos (`7147d55`, `a614be8`). |
| CR-4.2 | No cumple | No cumple | No cumple (mejora parcial) | Se capturó la excepción de la carpeta `data` (`c1e29b3`); siguen dos `catch` vacíos en `cargarDatos()`. |
| CR-4.3 | No cumple | No cumple | Cumple, con observación | Mensaje claro cuando falta la carpeta `data` (`c1e29b3`); los responsables por defecto se cargan sin aviso. |
| CR-5.1 | Cumple | Cumple | — | 14 casos de prueba (CP-01 a CP-14). |
| CR-5.2 | No aplica | Cumple | — | Los 14 casos se ejecutaron en ambas versiones. |
| CR-5.3 | No aplica | Cumple | — | Comparación en el informe, sección 6.2. |
| CR-5.4 | Cumple | Cumple | — | Evidencias en `docs/evidencias/`. |
| CR-6.1 | Cumple | Cumple | — | Commits separados en `refactor:`, `fix:`, `style:` y `docs:`. |
| CR-6.2 | Cumple | Pendiente | Cumple | Tags ubicados en los commits finales de cada etapa tras el merge. |
| CR-6.3 | No cumple | Cumple | — | README agregado (`14135be`). |
| CR-6.4 | No aplica | Cumple | — | Refactorings, decisiones técnicas, checklist y evidencias en `/docs`. |
| CR-6.5 | Cumple | Cumple | — | `out/` excluida en `.gitignore`. |
| CR-7.1 | No cumple | No cumple | Cumple | Se eliminó `calcularDiasAbierta()` (`a5f1a28`). |
| CR-7.2 | No aplica | No cumple | Cumple | Se reformateó la clase (`1b15ac6`). |
| CR-7.3 | No aplica | Cumple | — | Las partes modificadas son más fáciles de leer. |

Los ítems CR-5.2, CR-5.3, CR-6.4, CR-7.2 y CR-7.3 no aplican a la versión inicial, ya que evalúan modificaciones o documentación de etapas posteriores.

El detalle completo de cada ítem (ubicación, observación y acción correctiva) se encuentra en el informe, sección 7.

## Dictamen

La versión refactorizada se considera **aceptada con mejoras pendientes**. A partir de la revisión se corrigieron los ítems CR-2.4, CR-4.3, CR-7.1 y CR-7.2, y se mejoraron parcialmente CR-3.4 y CR-4.2. Los ítems que no cumplen quedan justificados como mejoras pendientes, ya que requieren una reestructuración que excede el alcance de los refactorings realizados (ver informe, sección 8.4).