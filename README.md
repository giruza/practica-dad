# practica-dad: PECE COMPONENTES
Repositorio para la práctica de Desarrollo de aplicaciones distribuidas de GDDV+GIC de la URJC

# fase 1

## aplicación web
Nombre: PECE componentes

Descripción: PECE componentes se trata de una página de venta de productos de electrónica, en la que se ofrecen pantallas, teclados, ratones, antenas, etc; al mejor precio. PECE componentes solo ofrece sus grandes ofertas a los clientes registrados, así que los invitados pueden navegar por la tienda pero solo los usuarios pueden añadir productos a sus pedidos y comprarlos.

## entidades

· Cliente: cada uno de los usuarios que usan la página. Pueden ser usuarios registrados. Puede hacer pedidos de uno o varios productos.

· Pedido: incluye una serie de productos que un cliente quiere comprar. A la hora de realizar la compra, la página se encargará de enviar un mensaje al correo del usuario con una factura del pedido que acaba de realizar.

· Producto: cada uno de los productos presentes en la tienda, cuenta con un nombre, una descripción y un precio.

## servicio interno
El servicio interno se encargará de recoger los pedidos que hacen los clientes y procesar los datos para enviárselos al usuario correspondiente por correo.

## equipo
Sergio Rus González (s.rus@alumnos.urjc.es) (github: giruza)

# fase 2

En esta fase se debe definir e implementar la aplicación web con base de datos MySQL. Las pantallas que tendrá la aplicación serán inicialmente públicas, aunque hay que decidir cuáles son públicas y cuáles privadas para la fase 3. Sus HTML hay que generarlos mediante Mustache.

## navegación por la página

La página de inicio de la aplicación es /greeting, y desde aquí se puede acceder a la mayoría de pantallas de la aplicación. A continuación se muestra un diagrama del flujo de navegación por la página.

![Screenshot](Archivos_readme/Navegacion.png)

En la página principal encontramos, cuando accedemos sin logear, dos opciones: iniciar sesión, o registrarnos. Si ya tenemos una cuenta podemos iniciar sesión, si no, será necesario que nos registremos. Si intentamos acceder a una página que requiere login sin estar logueados se nos redirigirá a la página de login.

![Screenshot](Archivos_readme/greeting.PNG)

En la página de login, se nos pide introducir nuestro nombre de usuario y contraseña, si es correcto se nos envía de vuelta a greeting mostrando nuevas opciones, si no, seguimos en el login.

![Screenshot](Archivos_readme/login.PNG)

En cuanto al registro, se nos pide introducir los datos necesarios para crear un nuevo cliente: el nombre de usuario, la contraseña, el correo en el que recibiremos los mensajes y el DNI a modo de identificador. Una vez creada la cuenta nos redirige al inicio.

![Screenshot](Archivos_readme/logup.PNG)

Una vez logueados, podemos ir a logout si queremos cerrar sesión, en cuyo caso se nos notifica que se va a cerrar la sesión y luego se vuelve al inicio. Por otra parte, ya podemos acceder a la tienda, donde vemos la lista de productos disponibles para comprar.

![Screenshot](Archivos_readme/store.PNG)

Cuando añadimos los productos en los que estamos interesados, podemos ir a nuestro carro para ver la lista de productos que hemos seleccionado.

![Screenshot](Archivos_readme/cart.PNG)

Si estamos seguros de que tenemos los productos que queremos comprar, podemos iniciar el pedido. A continuación se nos pide introducir los datos necesarios para crear un pedido nuevo: el nombre del pedido, la dirección a la que se enviará, el método de pago y el municipio en el que se entrega.

![Screenshot](Archivos_readme/petition.PNG)

Una vez tenemos los datos del pedido, podemos confirmarlo, se nos informa de que se enviará un correo a nuestra dirección, y se nos redirige a inicio.

## modelo de datos

En los siguientes diagramas se muestra cómo quedaría el modelo de datos de la aplicación, expresado tanto en un diagrama entidad relación como en un diagrama de clases UML.

![Screenshot](Archivos_readme/entidadrelacion.png)

![Screenshot](Archivos_readme/UML.png)

# fase 3

En la fase 3 la aplicación ya debe ser completamente funcional, implementando seguridad con Spring Security de manera que las páginas que se habían previsto que fueran privadas lo sean. Además, se debe definir la interfaz de comunicación entre la aplicación y el servicio interno e implementar este de manera que sea funcional.

## clases y templates

A continuación se muestra un diagrama de clases de la aplicación, con las relaciones entre controladores, repositorios y entidades, así como el acceso a los templates HTML de la página. Esto se realiza de forma centralizada desde el Application Controller. Se ha utilizado un código de colores para mayor claridad.

![Screenshot](Archivos_readme/classesandtemplates.png)

## obtención de .jar

En el caso de que queramos desplegar la aplicación, necesitaremos eventualmente los archivos .jar que ejecutan las aplicaciones, tanto el servicio web como el servicio interno. Es bastante sencillo obtenerlos, el proceso es el siguiente:

· En STS, accedemos a los proyectos con los que hemos estado trabajando, tanto para el servicio web como para la API REST. Nos aseguramos de que los cambios estén guardados y todo correcto antes de construir el .jar.

· Una vez asegurado esto, hacemos click derecho en el proyecto, seleccionamos Run as y dentro Maven build. Esto debería construir el proyecto devolviendo el archivo .jar, generalmente en la carpeta /target del proyecto.

· Si quisieramos lanzar esta aplicación directamente, lo podemos hacer desde la consola de comandos ejecutando
  java -jar {nombre del archivo .jar}

# fase 4

Esta es la fase final del proyecto, en ella no se introducen funcionalidades adicionales sino que se orienta a la tolerancia a fallos. Para ello es necesario desplegar la aplicación mediante contenedores docker, implementando técnicas que permitan que la aplicación funcione aunque se caiga algún nodo y que todos los servicios funcionen aunque haya un balanceador de carga.

## despliegue

El despliegue se realiza de la siguiente forma. Como podemos comprobar en el diagrama a continuación, se ha levantado en un docker cada una de las aplicaciones implicadas, así como la base de datos MySQL. El balanceador Haproxy tiene su propio docker y se encarga de conectar los front ends con los back ends para redirigir a los puertos correspondientes.

![Screenshot](Archivos_readme/dockers.png)

## instrucciones

Para poder realizar este proceso correctamente, lo primero que tenemos que hacer es instalar docker en nuestro sistema. Una vez que lo tenemos, ya podemos levantar los dockers necesarios. Para hacerlo directamente con este proyecto de github, lo descargamos y accedemos a la carpeta de Docker files. En ella abrimos una consola de comandos y ejecutamos lo siguiente.

Lo primero es crear la red y luego el docker de la base de datos y configurarla para que se pueda acceder a ella con la contraseña que tienen especificadas las aplicaciones.

docker network create our_net

docker pull mysql/mysql-server
docker run --name=mysql1 --network=our_net -d mysql/mysql-server
docker logs mysql1 2>&1
docker exec -it mysql1 mysql -uroot -p[password]
	ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'pass';
	create database test;
	create user 'root'@'%' identified by 'pass';
	grant all on test.* to 'root'@'%';

Después, ya podemos construir y ejecutar los dockers de las api rest;

docker build -f apirest-dockerfile -t "apirest" .
docker run -p 9998:9997 --name=apirest1 --network=our_net apirest
docker run -p 9999:9997 --name=apirest2 --network=our_net apirest

También los dockers de las aplicaciones web,

docker build -f application-dockerfile -t "application" .
docker run -p 9001:8443 --name=web1 --network=our_net application
docker run -p 9002:8443 --name=web2 --network=our_net application

Y por último del balanceador de carga Haproxy.

docker build -f haproxy-dockerfile -t "haproxy" .
docker run -p 1936:1936 -p 8080:8080 -p 9997:9997 -p 8443:8443 --name=haproxy --network=our_net haproxy


