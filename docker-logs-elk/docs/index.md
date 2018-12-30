# Docker logs with Elastic Stack(ELK, Filebeat)

Docker log messages are a very useful tool for a variety of IT tasks but simply using docker logs command is often not enough. Even with a few containers running is very difficult to find something useful in logs and even harder when you run docker over multiple machines. The best solution is to aggregate logs and then you can search for them and filter them, making them easier to work with. One of the most used tool for this kind of problem is Elastic Stack, also known as ELK stack.

For the completion of this tutorial, I suppose you have installed Docker and docker-compose. If you do not already have it, follow the official instructions . This article assumes that the Docker runs natively and the containers are accessible through localhost.

## About ELK

What is ELK? ELK is an acronym for an amazing and powerful collection of three open source projects developed by Elastic.co: Elasticsearch, Logstash, and Kibana.

ELK or also known as Elastic Stack is a complete end-to-end log analysis solution which helps in deep searching, analyzing and visualizing the log generated from different machines.


![alt text](./images/elk.png)

**Elasticsearch** is a distributed search and analytics engine based on the Lucene library. It provides a powerful RESTful JSON-based api, easy to use, scalable and resilient, schema free documents. Elasticsearch is the heart of the ELK stack, it centrally store your logs.

**Logstash** is a server-side data processing pipeline that ingests data from a multitude of sources simultaneously, parse it, transforms it, and then sends it to the Elasticsearch. Logstash has a pluggable framework with a lot of plugins to transform different inputs to enriched outputs for example there are plugins for transforming inputs from jdbc, kafka, log4j and other logs.

**Kibana** is a flexible visualization tool that consumes data stored in Elasticsearch. Using Kibana you can create line graphs, pie charts, histograms and more. In latests versions of Kibana you can use Vega grammar to build your own visualisations. "A picture’s worth a thousand line of logs".

![alt text](./images/kibana-timeseries.jpg)

## Filebeat

Filebeat is a open source lightweight shipper for logs written in Go and develope by Elastic.co, same company who developed ELK stack. Filebeat belongs to a bigger project called Beats and his purpose is to send data from hundreds or thousand of machines and systems to Logstash, Kafka, Redis or Elasticsearch. Beats family has shippers for all kind of data, for example:
 * Filebeat - Logs
 * Metricbeat - Metrics
 * Auditbeat - Audit Data
 * Packetbat - Network Data
 * Heartbeat - Uptime Data
 * Winlogbeat - Windows Event Logs
 
Filebeat helps you keep the simple things simple by offering a lightweight way to forward and transform log files using some internal modules(Nginx, MySQL, Docker, System).

![alt text](./images/filebeat-elk.png)

## Arhitecture

Assuming you have docker installed on your machine, by default standard output(stdout) off all containers is written into json files. These files are stored on host where docker engine is running under the path `/var/lib/docker/containers/{container-id}/{container-id}-json.log`.

Filebeat will scan files that match the following path `/var/lib/docker/containers/*/*-json.log` transform logs and then send them out to Logstash. Logstash will filter, transform and send them to Elasticsearch, once logs are saved in Elasticsearch you can use Kibana to build dashboards, to search for logs and all other features supported by Kibana.

![alt text](./images/arhitecture.png)

## ELK and Filebeat dockerfiles and configuration

Elastic Stack services will run as docker containers and Filebeat also. In this tutorial all containers except Filebeat container will be stateless. The strongest argument in favor of stateless container is that deployment is simple and it follows Java principle of “Build once run anywhere”. If you built a stateless image you don't need to worry about where to save container state data, or how to make containers interact with persistent storage.

### Elasticsearch Dockerfile

`FROM docker.elastic.co/elasticsearch/elasticsearch:6.5.2`

`COPY --chown=elasticsearch:elasticsearch elasticsearch.yml /usr/share/elasticsearch/config/`

`CMD ["elasticsearch", "-Elogger.level=INFO"]`

### Logstash Dockerfile

`FROM docker.elastic.co/logstash/logstash:6.5.2`

`RUN rm -f /usr/share/logstash/pipeline/logstash.conf`

`COPY pipeline/ /usr/share/logstash/pipeline/`

### Logstash conf 

`input {
    beats {
        port => 5044
        host => "0.0.0.0"
      }
    }`
    
`output {
    elasticsearch {
        hosts => elasticsearch
        manage_template => false
            index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
    }
   stdout { codec => rubydebug }
}`

The above configuration file tells to Logstash to accept input logs from beats on port 5044 and send them to Elasticsearch cluster with hosts found under “elasticsearch”. In Elasticsearch logs are stored in indexes with the following name pattern beat-{beat version}-{YYYY.MM.dd}

### Filebeat Dockerfile

`FROM docker.elastic.co/beats/filebeat:6.5.2`

`#` `Copy our custom configuration file
COPY filebeat.yml /usr/share/filebeat/filebeat.yml`

`USER root`

`#` `Create a directory to map volume with all docker log files
RUN mkdir /usr/share/filebeat/dockerlogs`

`RUN chown -R root /usr/share/filebeat/`

`RUN chmod -R go-w /usr/share/filebeat/`

The Filebeat configuration file same as Logstash needs a input and an output. This time input is a path where docker log files are stored and output is logstash.

Filebeat also is configured to transform files so keys and nested keys from json logs are stored as fields in Elasticsearch, this way we can query them, make dashboards and so on. Another interesting tool that Filebeat can do is adding some docker metadata to each log, metadata can be: docker image, service name from docker compose, container id and more.

`filebeat.inputs:
`-` type: docker
 combine_partial: true
 containers:
   path: "/usr/share/dockerlogs/data"
   stream: "stdout"
   ids:
     - "*"
 exclude_files: ['\.gz$\']
 ignore_older: 10m`
 
`processors:
 `#` decode the log field (sub JSON document) if JSON encoded, then maps it's fields to elasticsearch fields
`-` decode_json_fields:
   fields: ["log", "message"]
   target: ""
   `#` overwrite existing target elasticsearch fields while decoding json fields   
   overwrite_keys: true
`-` add_docker_metadata:
   host: "unix:///var/run/docker.sock"`
   
`#` `setup filebeat to send output to logstash
output.logstash:
 hosts: ["logstash"]`
 
 ## Deploy containers
 
 1. `git clone https://github.com/cosminseceleanu/tutorials.git`
 2. `cd tutorials/docker-logs-elk`
 3. Build and start containers using `docker-compose -f docker-compose.yml up -d`
 4. Checking containers status using `docker-compose -f docker-compose.yml ps` and you should have 5 containers
 
 ![alt text](./images/containers.png)
 
Container with name app is simple bash script who prints the following json {\"app\": "dummy", "foo": "bar"} message every two seconds. This message will be transformed and in Elasticsearch json keys “app”, “foo” should be fields in index and we can query them.

Using Kibana, which can be used using http://localhost:5601, under Discover tab type in search input “foo:bar” and press enter. You should see messages printed from docker container like the image below.

![alt text](./images/kibana1.png)

![alt text](./images/kibana2.png)

Besides log message printed from our dummy app the log message is enriched with metadata from filebeat like: beat.hostname, beat.name, bean.version and more but also the log will have some docker metadata.
 
This metadata can be used to search logs for example you search using docker compose service `docker.container.labels.com.docker.compose.service: app` or by docker image name `docker.container.image: dummy`.

## Resources:
1. https://docs.docker.com/
2. https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
3. https://www.elastic.co/guide/en/beats/filebeat/current/index.html
4. https://www.elastic.co/guide/en/kibana/current/index.html
5. https://www.elastic.co/guide/en/logstash/current/index.html
6. https://docs.docker.com/compose/



