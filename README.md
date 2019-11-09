## Scafolding  Process Aware Software(ProcessApp) with  pHipster.

get the image
-------------
docker login

docker container run --name jhipsterUntacit -v ~/sandbox/pwHipster/apps:/home/jhipster/app -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t utelemaco/jhipsteruntacit:0.0.2

attach shell
------------
docker container exec -it jhipsterUntacit bash

create the app
--------------
mkdir orderbook
cd orderbook
yo jhipster-untacit (make sure you choose Angular 1.x  and Maven)
gulp inject
./mvnw

add process file
----------------
add process file using the ProcessApp 
Account -> Login
Entities -> Process Definition -> Create a new Process Definition

config JSON files for entities
----------------
:TODO

scafold Process and Domain entities
--------------------------
yo jhipster-untacit:entity-domain OrderBookDomain --regenerate
yo jhipster-untacit:entity-process OrderBookProcess --regenerate

scafold Task entities
---------------------
yo jhipster-untacit:entity-task TaskSelectBook --regenerate

develop service tasks (delegate classes)
----------------------------------------
:TODO 

check gateways paths
--------------------
:TODO
