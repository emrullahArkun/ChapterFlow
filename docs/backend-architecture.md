# Backend-Architektur

## Überblick

Das Backend verwendet eine feature-orientierte Paketstruktur mit lokalen Schichten innerhalb jedes Features.

```text
com.example.readflow
├── auth
│   ├── api
│   ├── application
│   ├── domain
│   └── infra
├── books
│   ├── api
│   ├── application
│   ├── domain
│   └── infra
├── discovery
│   ├── api
│   ├── application
│   ├── domain
│   └── infra
├── sessions
│   ├── api
│   ├── application
│   ├── domain
│   └── infra
├── stats
│   ├── api
│   ├── application
│   ├── domain
│   └── infra
└── shared
```

Das ist keine rein horizontale Schichtenarchitektur wie `controller/service/repository`.
Fachlich zusammengehöriger Code bleibt pro Feature zusammen und wird erst innerhalb des Features geschichtet.

## Verantwortlichkeiten Der Schichten

### `api`

HTTP-nahe Codebestandteile.

- Controller
- Request-/Response-DTOs
- HTTP-Mapping
- API-Mapping

Beispiele:

- `books.api.BookController`
- `sessions.api.ReadingSessionController`
- `stats.api.dto.StatsOverviewDto`

### `application`

Use-Case-Orchestrierung.

- Transaktionsgrenzen
- Koordination zwischen Repositories, Domain-Objekten und weiteren Abhängigkeiten
- Feature-spezifische Services

Beispiele:

- `books.application.BookService`
- `discovery.application.DiscoveryService`
- `stats.application.StatsService`

### `domain`

Fachmodell und fachliche Regeln eines Features.

- Entitäten
- Value Objects
- Enums
- fachliche Policies
- feature-spezifische Verträge und Konzepte

Beispiele:

- `books.domain.Book`
- `discovery.domain.BookDiscoveryProvider`
- `stats.domain.achievements.*`

### `infra`

Technische Adapter und Persistenzdetails.

- JPA-Repositories
- externe API-Clients
- Cache-Listener
- Bootstrap- und Setup-Klassen

Beispiele:

- `books.infra.persistence.BookRepository`
- `discovery.infra.external.OpenLibraryClient`
- `auth.infra.bootstrap.DataInitializer`

### `shared`

Nur technische, querschnittliche Bausteine, die wirklich von mehreren Features verwendet werden.

- Security
- Exception Handling
- Konfiguration
- Zeit-Helfer

`shared` ist kein Ausweichordner für fachlichen Feature-Code.

## Standard-Template Für Neue Features

Das ist die Standardstruktur für neue Features:

```text
feature/
├── api
├── application
├── domain
└── infra
```

Pragmatische Leitlinien:

- Wenn ein Feature sehr klein ist, darf es anfangs flacher bleiben.
- `infra` kommt erst dazu, wenn wirklich Persistenz, Events, Cache oder externe Integrationen existieren.
- Weiter unterteilen solltest du erst dann, wenn sich im Feature verschiedene Verantwortungen vermischen.

Für sehr kleine Features reicht oft:

```text
feature/
├── api
├── application
└── domain
```

Für größere Features sind zusätzliche Subdomains sinnvoll:

```text
stats/
└── domain
    ├── achievements
    ├── activity
    └── streak
```

## Arbeitsregeln

- Feature-Code bleibt zuerst im eigenen Feature-Paket.
- Controller liegen nur in `api`.
- Repositories liegen nur in `infra.persistence`.
- HTTP-DTOS liegen nur in `api.dto`.
- Wenn ein Feature wächst, zuerst innerhalb des Features tiefer schneiden statt neue globale Sammelpakete anzulegen.
- Wenn ein Feature unübersichtlich wird, zuerst in Subdomains aufteilen statt zurück zu einer globalen Schichtenarchitektur zu wechseln.

## Warum Diese Struktur

Diese Struktur ist ein Kompromiss zwischen Einfachheit und Wachstum.

Vorteile:

- Zusammengehöriger Code bleibt nah beieinander.
- Ein einzelnes Feature kann wachsen, ohne globale `controller/service/repository`-Pakete aufzublähen.
- Große Features lassen sich intern weiter aufteilen, ohne die gesamte Anwendungsstruktur umzubauen.
- Die Paketstruktur zeigt sowohl fachliche Verantwortung als auch technische Rolle.

## Grenzen Und Typische Probleme

Die Struktur ist nützlich, hat aber klare Grenzen.

### 1. `application` kann zum Sammelbecken werden

Wenn am Ende jede Orchestrierungsklasse einfach `IrgendwasService` heißt, ist die Struktur formal geschichtet, aber trotzdem unübersichtlich.

Typische Anzeichen:

- viele große Services
- gemischte Read-/Write-Logik
- Orchestrierung und Fachlogik in derselben Klasse

Was dann sinnvoll ist:

- fachliche Policies in `domain` extrahieren
- Use Cases feiner schneiden
- Unterpakete innerhalb des Features anlegen

### 2. `shared` kann zum Müllcontainer werden

Wenn feature-spezifische Helfer in `shared` landen, sind die Feature-Grenzen nur noch Fassade.

Typische Anzeichen:

- `shared` importiert Feature-Klassen
- Utility-Klassen werden nur von einem einzigen Feature benutzt

Was dann sinnvoll ist:

- den Code zurück in das fachlich zuständige Feature verschieben

### 3. Features können trotzdem zu breit werden

Eine feature-orientierte Struktur löst keine schlechten fachlichen Schnitte automatisch.

Typische Anzeichen:

- ein Feature enthält mehrere kaum zusammenhängende fachliche Themen
- in einem Paket liegen viele Klassen, die nur lose verbunden sind

Was dann sinnvoll ist:

- das Feature in kleinere Subdomains aufteilen
- isolierte fachliche Konzepte in eigene Feature-Pakete auslagern, wenn sie eigenständiges Verhalten haben

### 4. Das ist keine strikte Clean Architecture

Das Backend nutzt eine geschichtete Feature-Struktur, aber keine vollständige Dependency Inversion an jeder Stelle.
Einige Klassen dürfen also weiterhin auf Spring-Konventionen oder API-nahe Typen angewiesen sein.

Das ist bewusst pragmatisch.
Wenn später echte hexagonale oder Clean-Architecture-Regeln nötig werden, muss das eine bewusste nächste Evolutionsstufe sein und darf nicht einfach aus der Paketstruktur abgeleitet werden.

### 5. Feature-übergreifende Abläufe können unübersichtlich werden

Wenn sehr viele Anwendungsfälle mehrere Features gleichzeitig betreffen, kann die Paketstruktur sauber aussehen, während der tatsächliche Abhängigkeitsgraph immer verworrener wird.

Typische Anzeichen:

- viele Services hängen an mehreren anderen Features
- fachliche Regeln verteilen sich ohne klaren Eigentümer über mehrere Features

Was dann sinnvoll ist:

- fachliche Ownership klären
- eine eigene Domain-Service- oder Feature-Grenze für diese Fähigkeit schaffen

## Wann Die Struktur Neu Bewertet Werden Sollte

Die Struktur sollte hinterfragt werden, wenn eines oder mehrere dieser Signale auftreten:

- neue Anforderungen betreffen fast immer mehrere Features gleichzeitig
- `application`-Pakete werden zu großen Orchestrierungszentren
- das Domain-Modell braucht deutlich stärkere Isolation von Frameworks
- Adapter und Infrastrukturthemen dominieren die Codebasis

Wenn das passiert, ist die nächste sinnvolle Stufe meist nicht eine rein globale Schichtenarchitektur.
Meist sind diese Schritte sinnvoller:

- mehr Subdomains in großen Features
- strengere Abhängigkeitsregeln zwischen den Schichten
- explizitere Ports-/Adapter-Strukturen in ausgewählten komplexen Features

## Zusammenfassung

Der Standard für dieses Backend ist:

- feature-orientierte Top-Level-Struktur
- lokale `api/application/domain/infra`-Schichten innerhalb jedes Features
- `shared` nur für echte querschnittliche Technik

Wenn ein Feature wächst, wird zuerst das Feature intern weiter geschnitten.
Es wird nicht automatisch zurück zu einer globalen `controller/service/repository`-Struktur gewechselt.
