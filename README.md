

## Oslo bysykkel klient 

Programmet viser en enkel status på alle tilgjengelige sykkelstativer i Oslo, gjennom det åpne API'et til oslobysykkel.no 

Det er programmert i Scala, med jackson og Unirest, samt Spark Framework for utviklingstesting.

Programmet bygges med SBT. Det er kun tatt høyde for å bygge og kjøre programmet fra SBT, med oppsettet for å lage kjørbar jar fil skal være komplett, selv om det ikke er testet i dette prosjektet 

## Kjøring

Det ligger en mal til en konfigurasjonsfil, som heter bysykkel.conf.mal. Kopier filen og endre navn til "bysykkel.conf" og rediger konfigurasjonen for å legge til produksjonsserver og autentiseringsnøkkel.  

* Rediger konfigfilen, _bysykkel.conf_
* Start sbt i rot katalogen, _$> sbt_
* Kjør programmet ThomasErUteAaSykler: _sbt> run_ 
* Avslutt sbt: _sbt> exit_
