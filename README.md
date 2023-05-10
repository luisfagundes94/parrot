# Parrot
A word reminder android app!

## Screenshots 
<img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot1.jpg?raw=true" width="200" /> 
<img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot2.jpg?raw=true" width="200" /> 
<img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot3.jpg?raw=true" width="200" />
<img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot4.jpg?raw=true" width="200" />

## Layers

### Project Structure
<p align="center"><img src="art/project.png" alt="Project Structure" width="500"></p>

### Plugins
The ```plugins``` layer is responsible for dependency management. It control and manage all dependencies in one place with Kotlin using the Gradle Version Catalog.

### Commons
The ```commons``` layer is responsible for common properties. It contains the implementations of the resources files, themes and components declared in the common layer.

- __testing__: This is responsible for common testing rules.

- __ui__: Has common UI components across the app.

- __theme__: Defines themes, colors, fonts and resource files.