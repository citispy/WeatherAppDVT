# WeatherAppDVT

This is my submission for the DVT Tech Task that is part of the interview process.

The task was to create an Android app that displays the current weather and a 5 day forecast using the OpenWeatherMap Api.

The app attempts to get the user gps location but if it fails to do so, the user is given the option to set their location manually using the PlacesApi.

Requests are made to:
- api.openweathermap.org/data/2.5/weather
- api.openweathermap.org/data/2.5/forecast

Requests are made using city name as a parameter. I decided to use the city name in the request because when sending coordinates, the values received back are not accurate.
The city name is retrieved in two ways, either from Geocoding using coordinates, or from the Places API, depending on whether the location is retrieved from GPS or 
PlacesAPI.

## Technical decisions made
I tried to use as much techical approaches that are recommended by Google. Some of these decisions are listed below:
- MVVM for the architecture
- The repository pattern to pull data into ViewModels
- Retrofit to make API calls
- Hilt for Dependency Injection
- NavComponent for displaying fragments
- LiveData for updating the UI
- JUnit4, Android Test Library, Android Architecture Components Core Test Library, and Robolectric for writing unit tests
- EasyPermissions for requesting permissions
