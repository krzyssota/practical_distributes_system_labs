# NoSQL databases

In this laboratory we will configure a simple NoSQL database cluster using
*[Aerospike](http://https://aerospike.com/)* Community Edition. The cluster
will consist of 2 nodes with in-memory data storages. We will also run a single instance
of *[Spring Cloud Schema Registry](https://github.com/spring-cloud/spring-cloud-schema-registry)*
to provide schema resolution. Both Aerospike server instances and Schema Registry
instances should be installed on the virtual machines provided by RTB House.

The laboratory will be focused around a REST application with sources located in the
`nosql/` directory. The application is a simple CRUD which
creates/reads/updates/deletes objects defined with the *[Apache Avro](https://avro.apache.org/docs/1.9.1/)* schema.
The application communicates via JSON messages which are then converted to/from Avro format
and stored to/read from the Aerospike database. The application depends on the Schema
Registry instance and registers the current schema upon start. You will have to run
the application against your configured server and then do some additional tasks
defined below.

Preferably you should to run the CRUD application from your local computer. To make
it work you need the Java JDK installed and an IDE - IntelliJ Idea is preferred.
You can also use Maven build system, if you don't want to use IDE (not recommended).

Import the Maven project with existing sources to have the application ready for deployment.


## 1. Prerequisites

You need to select two machines to serve as Aerospike nodes and one machine to serve
as a Schema Registry instance. The exemplary choice could look like this:

| Address                | Purpose       |
| ---------------------- | ------------- |
| stXXXvm101.rtb-lab.pl  | *Aerospike node 1*     |
| stXXXvm102.rtb-lab.pl  | *Aerospike node 2*     |
| stXXXvm103.rtb-lab.pl  | *Schema Registry instance*  |

Where 'XXX' is your student account number.

## 2. Aerospike servers installation

Perform operations from this section on both of your Aerospike servers.

Download the Aerospike Community Version installation package:

```bash
wget -O aerospike.tgz https://download.aerospike.com/download/server/latest/artifact/ubuntu20
```

And install the server:

```bash
tar xzvf aerospike.tgz
cd aerospike-server-community-5.7.0.16-ubuntu20.04/
sudo ./asinstall
```

Create the logging directory:
```bash
sud mkdir /var/log/aerospike
```

Clone the laboratory repository:
```bash
git clone https://github.com/RTBHOUSE/mimuw-lab.git
```

Copy the Aerospike server configuration file:
```bash
sudo cp mimuw-lab/lab07/aerospike/aerospike.conf /etc/aerospike/
```

Edit the `/etc/aerospike/aerospike.conf` file.
Replace the placeholders and put the correct IP addresses for the aerospike server nodes
in the `network` section of the configuration file.

To check the current server IP address, use the command:
```bash
ip -f inet addr show eth0 | grep inet
```

Run the server on both nodes and verify the status:
```bash
sudo systemctl start aerospike
sudo systemctl status aerospike
```

Check that the cluster contains two nodes:
```bash
asadm
Seed:        [('127.0.0.1', 3000, None)]
Config_file: /root/.aerospike/astools.conf, /etc/aerospike/astools.conf
Aerospike Interactive Shell, version 2.4.0

Found 2 nodes
Online:  10.112.102.101:3000, 10.112.102.102:3000

Admin> info
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Network Information (2022-04-10 13:58:02 UTC)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
               Node|         Node ID|                 IP|     Build|Migrations|~~~~~~~~~~~~~~~~~~Cluster~~~~~~~~~~~~~~~~~~|Client|  Uptime
                   |                |                   |          |          |Size|         Key|Integrity|      Principal| Conns|
10.112.102.101:3000|*BB99FA92BF77A86|10.112.102.101:3000|C-5.7.0.16|   4.024 K|   2|64F2554A011C|True     |BB99FA92BF77A86|     5|00:09:23
10.112.102.102:3000| BB974B84B500BEE|10.112.102.102:3000|C-5.7.0.16|   4.025 K|   2|64F2554A011C|True     |BB99FA92BF77A86|     6|00:10:09
Number of rows: 2

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Namespace Usage Information (2022-04-10 13:58:02 UTC)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Namespace|               Node|   Total|Expirations|Evictions|  Stop|~Disk~|~~~~~~~~~~~Memory~~~~~~~~~~~|~Primary~
         |                   | Records|           |         |Writes|  HWM%|       Used|Used%|HWM%|Stop%|~~Index~~
         |                   |        |           |         |      |      |           |     |    |     |     Type
mimuw    |10.112.102.101:3000|10.000  |    1.000  |  0.000  |False |     0|1010.000 B |    1|   0|   90|mem
mimuw    |10.112.102.102:3000|10.000  |    1.000  |  0.000  |False |     0|1010.000 B |    1|   0|   90|mem
mimuw    |                   |20.000  |    2.000  |  0.000  |      |      |   1.973 KB|     |    |     |
Number of rows: 2

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Namespace Object Information (2022-04-10 13:58:02 UTC)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Namespace|               Node|Rack|  Repl|   Total|~~~~~~~~~~~Objects~~~~~~~~~~~|~~~~~~~~~Tombstones~~~~~~~~|~~~~Pending~~~~
         |                   |  ID|Factor| Records|  Master|   Prole|Non-Replica| Master|  Prole|Non-Replica|~~~~Migrates~~~
         |                   |    |      |        |        |        |           |       |       |           |     Tx|     Rx
mimuw    |10.112.102.101:3000|   0|     2|10.000  | 0.000  |10.000  |    0.000  |0.000  |0.000  |    0.000  |0.000  |4.024 K
mimuw    |10.112.102.102:3000|   0|     2|10.000  |10.000  | 0.000  |    0.000  |0.000  |0.000  |    0.000  |4.025 K|0.000
mimuw    |                   |    |      |20.000  |10.000  |10.000  |    0.000  |0.000  |0.000  |    0.000  |4.025 K|4.024 K
Number of rows: 2

Admin>exit
```

The Aerospike servers should be now configured.

## 3. Running the Schema Registry

Now log to the machine which will host the Schema Registry.

Install the Java Runtime Environment:
```bash
sudo apt install -y openjdk-11-jre-headless
```

Download the Schema Registry jar file:
```bash
wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-schema-registry-server/1.1.5/spring-cloud-schema-registry-server-1.1.5.jar
````

Run the Schema Registry as a foreground process:
```bash
java -jar spring-cloud-schema-registry-server-1.1.5.jar > registry.log &
```

To stop it just read the `registry.log` file and check the process PID (every line
contains it) and just kill the application:

```bash
kill <pid>
```

The schema registry listens on TCP port **8990** by default.

## 4. Running the CRUD Application

Clone the repository to your computer:
```bash
git clone https://github.com/RTBHOUSE/mimuw-lab.git
```

As mentioned before you will have to build the application. You can either do it in the IntelliJ IDE (import project with existing sources) or use Maven for this purpose:

```bash
cd mimuw-lab/lab07/nosql
mvn clean install
```

**Notice**: if you use IDE, please rebuild the application using Maven in order to generate Java classes from Avro schema definition in `lab07/nosql/src/main/resources/schema/Message.avsc`.

Edit the configration file `lab07/nosql/src/main/resources/application.properties`
replacing the placeholders with appropriate data:
```properties
aerospike.seeds=<first node address>,<second node address>
aerospike.port=3000
spring.cloud.schemaRegistryClient.endpoint=http://<schema registry address>:8990
```

Now run the application
 - In IDE run the `com.rtbhouse.nosqllab.NosqlLabApplication` class.
 - Using Maven:
 ```bash
 mvn spring-boot:run
 ```

Test if the application works by writing a first record to the database:
```bash
curl -X PUT -H 'Content-type: application/avro-json' http://localhost:8080/endpoint/ -d '{"id":1, "field1": "test"}'
```
Read the written record:
```bash
curl http://localhost:8080/endpoint/1
```

Now write 10 records:
```bash
for i in $(seq 1 10); do curl -X PUT -H 'Content-type: application/avro-json' http://localhost:8080/endpoint/ -d '{"id":'$i', "field1": "test'$i'"}'; done
```

And read them:
```bash
for i in $(seq 1 10); do curl http://localhost:8080/endpoint/$i ; done
```

Your application should be running correctly.

## 5. Tasks

### 5.1 Observe the read and write throughput using `asloglatency` command.

Generate some traffic (for example 200 requests) on the application and run the
`asloglatency` command on one of the servers:

While running read requests:
```bash
sudo asloglatency -h {mimuw}-read
```

While running write requests:
```bash
sudo asloglatency -h {mimuw}-write
```

Observe the operations throughput.

### 5.2 Change the write consistency to `COMMIT_ALL` class and observe the changed write throughput using `asloglatency` command.

Adjust the `WritePolicy`'s `commitLevel` property in the `MessageDao` class.

### 5.3 Change the name of the `field1` in the `Message` schema and check if the application can read the records written with the previous schema.

Edit the `Message.avsc` file, change the name of the `field1` to `field2` and provide
field alias:
```
{"namespace": "com.rtbhouse.nosqllab",
  "type": "record", "name": "Message",
  "fields": [
    {"name": "id", "type": "long"},
    {"name": "field2", "type": ["null", "string"], "default": null, "aliases": ["field1"]}
  ]
}
```
**Be sure to rebuild the application afterwards!**

Reading any previously written record should return it contents but with the new `field2` name.

You can add some more changes to the schema, like an additional field, etc.
What kind of change would brake the schema compatibility?

### 5.4 Disable one Aerospike server node and check if the operations still end successfully.

```bash
sudo systemctl stop aerospike
```

And then check if write and read operations still work.

### 5.5 Provide endpoint for the `MessageDao.count()` operation mapped to the HTTP GET method.

The method is already implemented using UDF (User Defined Function) written in lua. Just provide the endpoint in the `NosqlResource` class.

### 5.6 Reimplement the count method to use the database scan.

Consult the *[documentation](https://developer.aerospike.com/client/java/usage/scan/scan_record)*.

Hint: define a private static class to implement the `ScanCallback` and to retain the count value.

### 5.7 Set the TTL of the record to 30 seconds in the `put` method.

Adjust the `WritePolicy`'s `expiration` property in the `MessageDao` class and observe if the written record disappears from the database after specified time.

### 5.8 Implement the delete method.

Implement the `delete` method in `MessageDao` class and map it to the HTTP `DELETE`
method in `NosqlResource` class.

### 5.9 Enable schema caching

By default the application must read the writer schema from the Schema Registry
every time when the record with non-matching schema is read. It is recommended
to cache the schemas within the application for performance reasons.

Hint: Set the appropriate option in the `SchemaRegistryConfig` class.
