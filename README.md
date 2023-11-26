# Onkostar Plugin mit Gen-Liste

Dieses Onkostar-Plugin ermöglicht das Abfragen von Genen, sowie weiteren Informationen zu einem Gen.

## Funktionalität

Das Plugin liefert eine Liste mit Genen und zu jedem Gen Informationen zu

* HGNC-ID
* Symbol
* EnsemblID
* Name
* Chromosom

Als Plugin ist es möglich, aus einem Formular ein Gen zu suchen und weitere Informationen zu einem Gen abzurufen.

Als Service kann es in anderen Plugins verwendet werden und ermöglicht hier den Zugriff auf die Liste mit Genen
innerhalb anderer Plugins.

### Beispiel zur Verwendung in einem Formularscript

Zum Suchen von Genen kann dieses Snippet in einem Formularscript verwendet werden:

```javascript
executePluginMethod(
    'GenesPlugin',
    'search',
    { q: 'BRAF' },
    function (result) {console.log(result);},
    false
);
```

Es wird hier eine Liste mit Genen ausgegeben, deren HGNC-ID, Symbol, EnsemblID oder Name den Wert "BRAF"
(ohne Berücksichtigung der Groß-/Kleinschreibung) enthalten.

Zum Abfragen eines einzelnen Gens kann auch das nachfolgende Snippet verwendet werden.
Hierbei wird jedoch ein Fehler geworfen, wenn kein passendes Gen (ohne Berücksichtigung der Groß-/Kleinschreibung)
gefunden wurde:

```javascript
executePluginMethod(
    'GenesPlugin',
    'findBySymbol',
    { symbol: 'BRAF' },
    function (result) {console.log(result);},
    false
);
```

### Beispiel der Verwendung in einem anderen Plugin

Zum Verwenden in einem anderen Plugin muss das Plugin in Onkostar installiert sein.

Im anderen Plugin wird die folgende Abhängigkeit benötigt:

```xml
<dependency>
    <groupId>dev.pcvolkmer.onkostar</groupId>
    <artifactId>onkostar-plugin-genes-api</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <!-- Optional, wenn nicht über Maven geladen -->
    <systemPath>${project.basedir}/libs/onkostar-plugin-genes-api.0.1.0-SNAPSHOT.jar</systemPath>
</dependency>
```

Diese Abhängigkeit beinhaltet die Schnittstellenbeschreibung für den zugehörigen Service, der durch das Plugin bereitgestellt wird.
Es kann wie folgt verwendet werden:

```java
public class GenesPlugin implements IProcedureAnalyzer {

    @Autowired
    private GeneService geneService;

    /* Weitere Implementierung des Plugins */

}
```

Dieser Service wird auch vom Plugin selbst verwendet: [GenesPlugin.java](impl/src/main/java/dev/pcvolkmer/onkostar/genes/GenesPlugin.java).

## Enthaltene Liste mit Genen

Es ist eine Liste mit über 43000 Genen von [https://genenames.org](https://www.genenames.org/cgi-bin/download/custom?col=gd_hgnc_id&col=gd_app_sym&col=gd_app_name&col=gd_pub_chrom_map&col=md_ensembl_id&status=Approved&hgnc_dbtag=on&order_by=gd_app_sym_sort&format=text&submit=submit)
enthalten.

Diese Liste der Gene unterliegt der folgenden Lizenz und ist frei verfügbar: [Creative Commons Public Domain (CC0) License](https://creativecommons.org/public-domain/cc0/).

## Build

Für das Bauen des Plugins ist zwingend JDK in Version 11 erforderlich.
Spätere Versionen des JDK beinhalten einige Methoden nicht mehr, die von Onkostar und dort benutzten Libraries verwendet
werden.

Voraussetzung ist das Kopieren der Datei `onkostar-api-2.11.1.5.jar` (oder neuer) in das Projektverzeichnis `libs`.

**_Hinweis_**: Bei Verwendung einer neueren Version der Onkostar-API muss die Datei `pom.xml` entsprechend angepasst
werden.

Danach Ausführen des Befehls:

```shell
./mvnw package
```