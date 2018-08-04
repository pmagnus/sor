# sor-adaptor

## http://filer.nsi.dk/sor/data/sor/sorcsv/v_1_2_1_19/

sor.csv

## http://filer.nsi.dk/sor/data/sor/sorxml/v_2_0_0/

sor.zip


## Usage

### Run the application locally

`lein ring server`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`

## License

Copyright ©  FIXME
