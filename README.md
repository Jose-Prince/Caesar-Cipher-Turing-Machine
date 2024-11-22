# Caesar-Cipher-Turing-Machine: Implementación del Cifrado César usando Máquinas de Turing

Este proyecto implementa un sistema de encriptación y desencriptación usando el cifrado César mediante la simulación de Máquinas de Turing. El proyecto es parte del curso de Teoría de la Computación 2024 y se centra en la implementación de máquinas de Turing para procesar texto mediante el algoritmo de cifrado César.

## Descripción del Proyecto

El cifrado César es una de las técnicas de encriptación más simples y conocidas. Funciona desplazando cada letra del texto original un número fijo de posiciones en el alfabeto. Esta implementación utiliza el concepto de Máquinas de Turing para realizar las operaciones de encriptación y desencriptación.

### Características Principales

* Encriptación y desencriptación usando el algoritmo César
* Implementación basada en Máquinas de Turing
* Soporte para letras minúsculas y mayúsculas
* Preservación de espacios en el texto
* Llave de desplazamiento configurable (k)
* Configuración de máquinas mediante JSON
* Suite completa de pruebas

## Estructura del Proyecto

```
├── src/
│   ├── main/java/com/mycompany/app/
│   │                   ├── App.java
│   │                   ├── Caesar_Cipher.java
│   │                   └── Machine.java
│   └── test/java/com/mycompany/app/
│                       ├── EncriptTest.java
│                       └── DesencriptTest.java
├── encrypt.json
├── desencrypt.json
├── pom.xml
└── README.md
```

## Requisitos

* Java JDK 11 o superior
* Maven
* Biblioteca Gson para parsing JSON

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/Jose-Prince/Caesar-Cipher-Turing-Machine.git
```

2. Navegar al directorio del proyecto:
```bash
cd Caesar-Cipher-Turing-Machine
```

3. Construir el proyecto:
```bash
mvn clean install
```

## Uso

1. Ejecutar la aplicación
2. Seguir las instrucciones para:
   * Ingresar el texto a encriptar/desencriptar (Formato: `k#mensaje` donde k es el valor de desplazamiento)
   * Elegir modo de operación (1 para encriptar, 2 para desencriptar)

### Ejemplos de Uso

1. Encriptación:
```
Entrada: 3#hola mundo
Opción: 1
Resultado: krod pxqgr
```

2. Desencriptación:
```
Entrada: 3#krod pxqgr
Opción: 2
Resultado: hola mundo
```

## Funcionamiento

El sistema utiliza tres cintas en la Máquina de Turing:
1. Cinta de entrada: Contiene el mensaje a procesar
2. Cinta del alfabeto: Contiene el alfabeto de referencia
3. Cinta de llave: Gestiona el valor de desplazamiento

Los estados de la máquina incluyen:
* q0: Estado inicial
* q1-q3: Estados de procesamiento
* q4: Estado de aceptación final

## Pruebas

Ejecutar la suite de pruebas:
```bash
mvn test
```

Las pruebas incluyen:
* Validación de lectura de archivos
* Verificación de inicialización de cintas
* Validación de transiciones
* Pruebas de flujo completo con diversas entradas

## Colaboradores

* José Prince, 22087
* Fabiola Contreras, 22787
* Maria Jose Villafuerte, 22129

## Documento del Proyecto
  El archivo Proyecto 3_ Máquinas de Turing.pdf contiene la especificación completa del proyecto.

