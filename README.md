# Xcale Test
## _Evaluación técnica para Carlos Escalona por parte de Xcale_
-------------------------------------------------------------------
### Tecnologías usadas:
- Java 11
- Spring Boot 2.7.9
- Base de Datos en memoria H2
- Lombok
- Junit 5
- Swagger 

-------------------------------------------------------------------

## Instalación/Ejecución
- Descargar y ejecutar el proyecto, puerto por defecto 8080.
- La base de datos está en memoria, por lo tanto, no es necesario configurar una conexión.
- Al momento de correr el proyecto se crea la Base de Datos.
- En caso que se necesite acceder a la BD se puede hacer por la url (http://localhost:8080/h2-console/) con los siguientes parámetros:
  * Driver Class:  org.h2.Driver 
  * JDBC URL: jdbc:h2:mem:devdb 
  * User Name: sa 
  * Password:  password 
- Existe un script que carga las tablas necesarias (user, group, user_group) para probar el sistema.
- El acceso a la documentación de los servicios rest del proyecto está en (http://localhost:8080/swagger-ui.html#/)
-------------------------------------------------------------------

## Funcionamiento
El sistema se enfoca principalmente en el servicio de enviar mensajes (http://localhost:8080/api/v1/whatsapp/send-message), para ello, se debe cargar el payload con la respectiva data:
`{"groupId": 1,
"senderId": 1,
"text": "mensaje",
"type": "TEXT"}`

###### groupId: Es el id del grupo a donde será enviado el mensaje. Para efectos prácticos siempre se debe usar el id 1.
###### senderId: Es el id del usuario emisor del mensaje. Se pueden usar los Id del 1 al 10 que ya están registrados en la BD.
###### text: Es un texto cualquiera que puede contener el mensaje.
###### type: Es el tipo de mensaje que se va a enviar. Se puede escoger entre los valores TEXT, VOICE, DOCUMENT, aunque para efectos prácticos no tiene relevancia en este proyecto el tipo de mensaje.  


-------------------------------------------------------------------

  El proceso de envío de mensajes consiste en:
  - Al envíar el mensaje se almacena en la tabla MESSAGE de la BD.
  - Se buscan todos los usuarios del grupo al que va dirigido el mensaje, excluyendo al remitente.
  - Se simula una notificacion a cada uno de los usuarios (se puede verificar a través de la consola del sistema)
  
  Para escalar la funcionalidad del sistema, en particular las notificaciones de los mensajes, se puede implementar un patrón observador o uno de subscripción.


[PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
[PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
[PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
[PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
[PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
[PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>


-------------------------------------------------------------------

## Documentación

### Modelo Entidad Relación (BD) 
![My Image](MER.png)

### Diagrama de Clases
![My Image](ClassDiagram.jpeg)

