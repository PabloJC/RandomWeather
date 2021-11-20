# Random Weather

Random weather is and app where we can know the weather of a random place

## Api

We have use https://openweathermap.org/current api

## App

![AppImage (1)](https://user-images.githubusercontent.com/9006477/142729507-ea6d6735-1d09-4db9-8db0-14447a127823.jpg)

This a modularized App using Clean Architecture and a MVVM design pattern. The structure is divided in some modules:

- app: It contains the Applicacion, ui, viewmodels, dependency inyections and the datasources implementation.
- domain: It contains the models that are used by every modules.
- usecases: It contains every usecases used by ViewModels to provide data to view.
- data: It contains repository implementation and datasources contract.
- testShared: It is a shared module for testing uses. It contains mocks and Fakes.

We have created UnitTest in modules using Mockk and dependency injections using Dagger Hilt



## Usage

```
# You need to add the MAPS_API_KEY in your local.properties file
MAPS_API_KEY  = AIzaSyBYVTTCr1YibZQTqPmptnKSID1SeEND4c0
```


