# Decisiones técnicas — Etapas 4 y 5

## 1. Estrategia de refactoring

Se decidió realizar modificaciones incrementales sobre el sistema existente, utilizando la rama `feature/refactoring`.

Cada técnica se implementó en un cambio separado y se registró mediante commits descriptivos para facilitar su seguimiento.

## 2. Priorización de mejoras

Se priorizaron los siguientes problemas del diagnóstico:

- **3.1 God Class:** reubicación de la creación de solicitudes.
- **3.2 Long Method:** extracción de métodos para seleccionar tipo y prioridad.
- **3.7 Poor Error Handling:** validación de entradas incorrectas.
- **3.10 Long Parameter List:** incorporación de `DatosSolicitud`.
- **3.11 Poor Naming:** mejora de nombres de variables.

También se corrigieron el cálculo del promedio y la dependencia de las rutas CSV respecto del directorio de ejecución.

## 3. Justificación de las decisiones

Se eligieron modificaciones acotadas para reducir el riesgo de introducir regresiones.

Se aplicó principalmente una mejora parcial de SRP y prácticas de Clean Code. No se realizó una reestructuración completa de la arquitectura ni se afirma haber aplicado todos los principios SOLID.

Se mantuvo la persistencia mediante archivos CSV y la interfaz por consola para respetar el alcance original.

## 4. Verificación

Se realizaron pruebas manuales después de los principales cambios y se ejecutaron los doce casos definidos durante el diagnóstico.

Las evidencias del comportamiento posterior se encuentran en `docs/evidencias/despues/`.

La comparación del código se encuentra en `docs/comparacion-antes-despues.diff`.

## 5. Limitaciones

La clase `SistemaSolicitudes` todavía concentra responsabilidades relacionadas con la interfaz, la lógica de negocio y la persistencia.

Como futuras mejoras se propone separar estas responsabilidades, encapsular los atributos públicos e incorporar pruebas automatizadas.