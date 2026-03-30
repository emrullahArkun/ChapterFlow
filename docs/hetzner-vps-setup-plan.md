# Hetzner VPS Setup Plan

## Ziel

MyBookTracker zuerst einfach und guenstig auf einem einzelnen Hetzner-Server starten:

- ein VPS
- Frontend, Backend und Postgres per Docker Compose
- Zugriff zuerst ueber die Server-IP
- spaeter optional Domain und HTTPS
- spaeter optional Upgrade auf groesseren Server

## Projektbewertung

Das aktuelle Projekt ist fuer einen kleinen Single-Server-Start gut geeignet:

- `frontend`: statisches Vite/React-Build, ausgeliefert ueber Nginx
- `backend`: Spring Boot API
- `database`: PostgreSQL
- kein Redis
- kein separater Worker
- kein S3/Object Storage
- kein Kubernetes

Das bedeutet: fuer den Start ist kein komplexes Infrastruktur-Setup noetig.

## Serverwahl

### Empfehlung fuer den Start

Starte mit `Cost Optimized CX23` plus `Primary IPv4`.

Warum:

- `CX23` hat `2 vCPU`, `4 GB RAM`, `40 GB SSD`
- fuer MyBookTracker ist `4 GB RAM` als Startwert deutlich sinnvoller als `2 GB RAM`
- mit GitHub Actions + GHCR muss der Server nicht lokal bauen, sondern nur fertige Images ziehen
- wenn es spaeter nicht reicht, kann auf `CX33` oder `CX43` hochskaliert werden

### Warum nicht einfach das kleinste Regular-Performance-Angebot?

Das kleine `Regular Performance`-Angebot ist fuer dieses Projekt nicht automatisch besser.

Laut aktueller Hetzner-Produktseite startet `Regular Performance` mit kleinen `CPX`-Instanzen, darunter `CPX11` mit `2 GB RAM`. Das ist zwar modernere Hardware, aber fuer dieses Projekt ist `4 GB RAM` am Anfang meist wertvoller als etwas bessere CPU-Leistung.

Fuer MyBookTracker ist daher als erster Schritt `CX23` oft sinnvoller als das kleinste `Regular Performance`-Angebot.

### Wichtige Entscheidung: x86 statt ARM

Wenn du mit dem jetzigen Setup startest, nimm fuer den Anfang ein `x86`-System:

- `CX23`, `CX33`, `CX43`
- nicht `CAX11`, `CAX21`, ...

Grund:

- die aktuellen GHCR-Images werden im CI derzeit ohne Multi-Arch-Setup gebaut
- damit ist `x86` der unkomplizierte Start
- `ARM` kann spaeter kommen, ist aber fuer den ersten Rollout unnoetig komplizierter

## Kostenrahmen

Stand: `30. Maerz 2026`

Offizielle Hetzner-Preise koennen sich je nach Standort, Steueranzeige und Auswahl von `IPv4` oder `IPv6 only` leicht anders darstellen. Fuer den Plan hier gilt als praktische Orientierung:

- `CX23`: sehr guenstiger Einstieg
- `Primary IPv4`: zusaetzlich noetig, wenn die Website normal aus dem Internet erreichbar sein soll
- `SSL`: spaeter mit Let's Encrypt typischerweise kostenlos
- `Backups`: am Anfang bewusst weggelassen

Praktische Startrechnung:

- `1x CX23`
- `1x Primary IPv4`
- `0x Backups`
- `0x Load Balancer`
- `0x Volumes` zunaechst

Ergebnis:

- sehr niedrige monatliche Startkosten
- spaeter einfach erweiterbar

## Warum Primary IPv4?

Fuer den Start ohne Domain willst du die Seite direkt per IP aufrufen koennen.

Das geht am einfachsten und am zuverlaessigsten mit `IPv4`.

Ohne `Primary IPv4`:

- waere die Seite nur ueber `IPv6` sauber direkt erreichbar
- das ist fuer eine erste oeffentliche Web-App unnoetig riskant
- viele Nutzer, Netze und Geraete sind zwar IPv6-faehig, aber nicht immer konsistent

Fuer diesen Startplan gilt deshalb:

- `Primary IPv4`: ja

## Was bei Hetzner konkret anzulegen ist

1. Neues Cloud-Projekt anlegen
2. Neuen Server erstellen
3. Servertyp waehlen: `CX23`
4. Standort waehlen: `FSN` oder `NBG`
5. Betriebssystem waehlen: `Ubuntu 24.04`
6. `Primary IPv4` aktivieren
7. SSH-Key hinterlegen
8. Firewall anlegen und zuweisen

## Firewall-Regeln

Minimal:

- `22/tcp` nur fuer deine eigene IP, wenn moeglich
- `80/tcp` offen
- `443/tcp` offen

Wenn du zuerst nur per IP und HTTP startest, reicht vorerst:

- `22/tcp`
- `80/tcp`

## Software auf dem Server

Nach dem Anlegen des Servers:

1. per SSH verbinden
2. System aktualisieren
3. Docker installieren
4. Docker Compose Plugin installieren
5. optional `ufw` nur wenn du nicht ausschliesslich die Hetzner-Firewall nutzen willst

## Deployment-Strategie

MyBookTracker sollte auf dem Server nicht selbst bauen.

Stattdessen:

1. GitHub Actions baut die Images
2. Images werden nach GHCR gepusht
3. der Hetzner-Server zieht nur die fertigen Images
4. `docker compose up -d` startet oder aktualisiert die Container

Das reduziert CPU- und RAM-Last auf dem VPS deutlich und macht `CX23` realistischer.

## Dateien, die auf den Server muessen

Minimal:

- `docker-compose.prod.yml`
- `.env`
- optional ein kleines Deploy-Verzeichnis, z. B. `/opt/mybooktracker`

## Beispielwerte fuer `.env`

```env
DB_USERNAME=postgres
DB_PASSWORD=ein-langes-zufaelliges-passwort
APP_JWT_SECRET=ein-langes-zufaelliges-jwt-secret-mit-mindestens-32-bytes
APP_CORS_ORIGINS=http://SERVER_IP
BACKEND_IMAGE=ghcr.io/emrullaharkun/mybooktracker-backend:latest
FRONTEND_IMAGE=ghcr.io/emrullaharkun/mybooktracker-frontend:latest
```

## Start ohne Domain

Fuer die erste Phase:

- Zugriff ueber `http://SERVER_IP`
- kein HTTPS zwingend noetig
- keine Domain noetig

Das ist fuer einen ersten funktionierenden Rollout voellig okay.

## Spaeter: Domain und HTTPS

Sobald die App stabil auf dem VPS laeuft:

1. Domain kaufen
2. DNS auf die Server-IP zeigen lassen
3. HTTPS aktivieren

Am einfachsten spaeter mit:

- `Caddy`

Alternativ:

- `Nginx + Certbot`

## Wann auf einen groesseren Server wechseln?

Upgrade von `CX23` auf `CX33` oder `CX43`, wenn:

- Deployments langsam oder instabil werden
- der RAM regelmaessig knapp wird
- Postgres und App gemeinsam den Server zu stark belasten
- mehrere Nutzer gleichzeitig merklich Performance-Probleme sehen

## Nicht sofort noetig

Am Anfang bewusst weglassen:

- separater Datenbank-Server
- Redis
- Load Balancer
- Object Storage
- automatische Backups
- Domain
- HTTPS
- Kubernetes

## Empfohlene Reihenfolge

1. Hetzner `CX23` mit `Primary IPv4` anlegen
2. Firewall und SSH einrichten
3. Docker auf dem Server installieren
4. GHCR-Zugriff fuer den Server einrichten
5. `docker-compose.prod.yml` und `.env` auf den Server legen
6. Container starten
7. App ueber die Server-IP testen
8. Erst danach Domain und HTTPS nachziehen

## Entscheidung fuer den ersten Rollout

Fuer MyBookTracker ist der pragmatische Start:

- `Cost Optimized`
- `CX23`
- `Primary IPv4`
- `x86`
- `ohne Domain`
- `ohne HTTPS`
- `mit GHCR-Images`

Das ist der guenstigste vernuenftige Start mit sauberem Upgrade-Pfad.
