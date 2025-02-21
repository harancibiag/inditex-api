# API INDITEXT

# HANS ARANCIBIA - PROYECTO EVALUACION

### Guia del usuario

### Ejecución del servicio
Se debe ejecutar el programa java
inditex-api\src\main\java\es\inditex\inditexapi\InditexApiApplication.java

#### Pantalla principal
<img height="400" src="/src/main/resources/static/pantalla1.png" width="400"/>

### Implementación de Spring Security (/auth/log-in)
Se implementa seguridad (no solicitada va como valor agregado) con la 
finalidad de que no accesan usuarios no autorizados, los datos para ingresar son:
username -> ADMIN    clave->1234
<img src="/src/main/resources/static/pantalla5.png" width="800"/>

**Se debe copiar el jwt-token y pegarlo dentro del boton "Authorize"
para lograr acceder al servicio**
<img src="/src/main/resources/static/pantalla6.png" width="800"/>
<img src="/src/main/resources/static/pantalla7.png" width="800"/>

### Endpoint /prices (GET)
Se implementa el servicio retornando los valores solicitados en la evaluacion.
Los parametros son:
Fecha de aplicación en formato yyyy-MM-dd HH:mm:ss (ejemplo: 2020-06-14 10:00:00)
Id del Producto en numero entero (ejemplo: 356455)
Id de la Cadena en numero entero (ejemplo: 1)

El retorno va con lo solicitado que para el caso de los ejemplos es:
{
    "productId": 356455,
    "brandId": 1,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "priceAmount": 35.5
}
<img src="/src/main/resources/static/pantalla4.png" width="800"/>

### TEST DE CLASES 
<img src="/src/main/resources/static/pantalla2.png" width="400"/>

Se implemento el testeo de las clases utilizando mockito, 
se realizan 5 test comprobando que deben retornar los valores
correctos, para validarlo debe ejecutarse el programa java
PriceServiceImplTest.java ubicado en la 
inditex-api\src\test\java\es\inditex\inditexapi\service\implementation

### TEST INTEGRAL
<img src="/src/main/resources/static/pantalla3.png" width="400"/>

Se implemento el testeo integral como servicio, realizandose tambien
los 5 test validando que, esta vez el servicio web, retorne los valores
correctos, para validarlo debe ejecutarse el programa java PriceControllerIntegrationTest.java
ubicado en
inditex-api\src\test\java\es\inditex\inditexapi\presentation\controller

### RESUMEN DE LA CODIFICACION
Se ejecutó lo solicitado, aplicando para la base de datos H2.

la misma nomenclatura de variables, aplicando CLEAN-CODE y SOLID separando 
las validaciones del servicio en si para mejorar el mantenimiento/escalabilidad 
de las mismas sin afectar al servicio principal.

Se cumple con la validacion linter (se usa SonarQube) dejandose solo algunas alertas
en los metodos del testing que no afectan a produccion.

Se implementa la gestion de excepciones y el advice con la clase ErrorHandler

Se implementan diferentes codigos http de error, especificos por HttpStatus.NOT_FOUND
HttpStatus.BAD_REQUEST y otros errores

Se separan las capas y se evitan acoplamientos 

Se optimiza la consulta a la base de datos (se incluye integridad referencial de 
la tabla PRICES contra BRANDS no solicitada)

