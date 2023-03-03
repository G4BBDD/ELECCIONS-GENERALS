# M02 UF2 ELECCIONS GENERALS G4

Projecte desenvolupat en Java amb el propòsit d'extreure dades dels arxius del Ministerio de l’Interior on hi ha dades dels processos electorals en fitxers .DAT per  importar-les en una base de dades.

## Funcionament de les importacions

- A l'executar el programa, ha d'escollir l'opció d'importar dades a la bd.
- El programa farà la importació de cadascuna de les taules mitjançant el codi de cada clase.

## Paquets

Hem dividit el projecte en 2 paquets principals: Importar i LleiHondt

- Importar
- LleiHondt

Dins del paquet d' Importar podem trobar: 

## Class

Totes les clases segueixen una estructura, hi ha una part dedicada a fer la connexió a la base de dades, seguidament es fan les "SELECTS" per assignar les id de les FK's

- ImportarComunitatsAutonomes
    - Aquesta classe s'encarrega de fer la connexió a la base de dades amb les credencials que té definides, llegeix l'arxiu .DAT que esta ubicat en una ruta X i agafa les dades que té l'arxiu.
    
- ImportarProvincies
    - Aquesta classe s'encarrega de fer la connexió a la base de dades amb les credencials que té definides, llegeix l'arxiu .DAT que esta ubicat en una ruta X i agafa les dades que té l'arxiu. També necesita fer una SELECT per agafar el valor de la "comunitat_aut_id" de la taula "provincies" i asignar-la a la "comunitat_aut_id" de la taula actual.

- ImportarPersones
    - Aquesta classe s'encarrega de fer la connexió a la base de dades amb les credencials que té definides, llegeix l'arxiu .DAT que esta ubicat en una ruta X i agafa les dades que té l'arxiu. Aquest codi també s'encarrega de comprovar si els valors de "dni" i "data_naixement" tenen com valor "0000-00-00" en el cas de "data_naixement" i "000000000" en el cas de "dni", el programa posara aquest valors com nulls.

- ImportarMunicipis
    -  Aquesta classe s'encarrega de fer la connexió a la base de dades amb les credencials que té definides, llegeix l'arxiu .DAT que esta ubicat en una ruta X i agafa les dades que té l'arxiu. També necesita fer una SELECT per agafar el valor de la "provincia_id" de la taula "municipis" i asignar-la a la "provincia_id" de la taula actual.

- ImportarPartitsPolitics / ImportarCandidatures
    - Aquesta classe s'encarrega de fer la connexió a la base de dades amb les credencials que té definides, llegeix l'arxiu .DAT que esta ubicat en una ruta X i agafa les dades que té l'arxiu. També s'encarrega de definir la "eleccio_id" com "1" ja que només tenim aquesta a la base de dades i hem decidit posar-ho manualment al codi.

- ImportarEleccionsMunicipis

## Incidents
Els problemes a l'hora de realitzar l'activitat i les solucions proposades han estat el següents:
- La data de naixement a la taula "persones" és tot zeros. La solució ha estat donar el valor "null" al camp quan es doni aquest cas.
- El DNI no és únic, y consisteix en tot zeros. La solució ha estat treure la constraint "unique key" al camp i donar-li el valor null quan es doni aquest cas.

## Autors

Aquesta activitat ha estat realitzada per : 
-   Elyass el Jerari
-   Alvaro Gallego
-   Rosen Kaloyanov
-   Adam Boulahfa
