# Natural-Systems-Simulator

An interactive natural-systems simulator with a JavaFX interface and the graphic
sketches rendered with Processing.

This project is an **improvement and reorganization of the practical projects**
I developed for the **Modelação e Simulação de Sistemas Naturais (MSSN)** course
of the **Licenciatura em Engenharia Informática e Multimédia (LEIM)** at
**ISEL** (Instituto Superior de Engenharia de Lisboa).

The goal is to take the original sketches and turn them into a cohesive
application: a single entry point (`ui.MainApp`), a central simulation catalog,
error handling through logging, no duplicated code and automated tests.

---

## Requirements

- **JDK 27** installed (`java -version` should report 27 or newer).
- Internet access on the **first run** (Gradle downloads the dependencies and
  the Gradle distribution through the included wrapper).
- Gradle does **not** need to be installed manually: the project ships with the
  Gradle wrapper.

---

## Getting started

Run the commands below from the project root.

**Windows (PowerShell / CMD):**

```powershell
.\gradlew.bat run
```

**Linux / macOS:**

```bash
./gradlew run
```

The main window opens with the navigation sidebar (`Home`, `Simulation`,
`About`). On the **Simulation** page, click a card to launch the corresponding
sketch in its own Processing window and separate process.

### Building a distribution

Creates a runnable `distZip`/`distTar` under `build/distributions/`:

```powershell
.\gradlew.bat build
```

---

## Testing

```powershell
.\gradlew.bat test
```

The tests check the integrity of the simulation catalog (unique identifiers,
lookup by id and creation of the runnable simulations).

---

## Author

**Rafael Dias** — 2026
Academic work developed for the MSSN course, LEIM, ISEL.

Educational project, provided without any warranty. Use it at your own risk.
