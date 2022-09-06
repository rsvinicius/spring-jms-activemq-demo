# Spring JMS

The Java Message Service (JMS) is a Java API that defines a common set of interfaces that allow applications to securely pass along and receive messages.

## Description

JMS usage sample using ActiveMQ. 

There are four available connections configurations for jms available in this project:
- Application.yaml (JmsConfigApplicationYaml)
- ActiveMQ Connection Factory (JmsConfigActiveMQ)
- Single Connection Factory (JmsConfigSingleConnection)
- Caching Connection Factory (JmsConfigCachedConnection) - **Default**

Also, concepts such as message converters, transaction management, custom headers and response management are used in this application.

## Requirements

- Java 11+
- IntelliJ IDEA / Netbeans / Eclipse
- Docker (for ActiveMQ)

## Usage

1) Run docker-compose up in CLI to start a local MongoDB database

   The docker compose file should look like this (file included in project root directory):
    ```
    version: '3.3'

    services:
      activemq:
      image: rmohr/activemq
      container_name: activemq
      restart: always
      ports:
        - "61616:61616"
        - "8161:8161"
    ``` 
2) Start application in IDE or via command line:

    ```
    ./gradlew bootRun
    ```  
   
3) Enjoy!

## Documentation

- [Swagger](http://localhost:9080/swagger-ui/index.html#/) 
- [ActiveMQ](http://localhost:8161/) (Credentials available in application.yaml)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.  Please make sure to update tests as appropriate.

## License

Usage is provided under the [MIT License](https://mit-license.org/). See LICENSE for full details.