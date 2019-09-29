# aSharp-library

![Alt text](readme-resources/app_icon.png?raw=true "App icon")

## TL;DR

Features: paginated search, deletable previous searches, details

Architecture choices: hexagonal approach, partial Android architecture components usage

Difficulties: stome struggle with API 19 & Retrofit,
fragment pager adapter & testing live-data with coroutines 

## Application features

As required, the application features:

###### Seaching an artist/band

![Alt text](readme-resources/screenshot_search.png?raw=true "Screenshot search")

* A progress bar appears during search
* Search is throttled, in order to minimize calls to the MusicBrainz API
* A label with the number of results is displayed
* Results are ordered by pertinence *score* then by *sort name*
* Results are paginated, scrolling to the end of the list will load the next page
* Result item displays sort name, *disambiguated type* and a hypothetical list of relevant *tags*
* A touch on a result item will open the artist details
	
###### Display a list of previously visited artists

![Alt text](readme-resources/screenshot_previous_searches.png?raw=true "Screenshot previous searches")

* Previous searches are ordered by date (most recent first)
* Previous search item displays sort name, disambiguated type and a delete button
* A touch on the delete button, as well as a swipe, will delete the entry
* A touch on the item will open the artist details

###### Display artist details

![Alt text](readme-resources/screenshot_details_discography.png?raw=true "Screenshot details discography")
![Alt text](readme-resources/screenshot_details_about.png?raw=true "Screenshot details about")
![Alt text](readme-resources/screenshot_details_discography_tablet.png?raw=true "Screenshot details discography tablet")
![Alt text](readme-resources/screenshot_details_about_tablet.png?raw=true "Screenshot details about tablet")

* An header displays the name and a random full-width front cover
* The header will collapse when scrolling to give more space to the elements below
* A discography tab displays a list of all the release groups with the small front cover, the title and the release date
* On tablet, discography will be displayed as grid instead of a list, with a dynamic number of items by line (notably more items will be displayed on a line in landscape than in portrait)
* An about tab displays sections of information such as the gender, the date of birth / foundation, the date of death / dissolution, the country, the legal IPI and ISNI codes, the overall rating, a wikipedia extract and a "learn more" button that will open a navigator query with the artist name.
* On tablet the about tab sections are organized slightly differently in order to use the space more efficiently

## Architecture

I like Martin Folwer & Ralph Johnson vision of what *architecture* means. It could be summarized in "the **important stuff**, whatever that is" or "every **significant decisions** made so far". 

Here are the important choices that I made during the test.

### Using the "hexagon" approach

In an *hexagonal* architecture -also known as *ports & adapters*-, the *domain* (core model & business logic) is decoupled from the rest of the system : it depends on nothing and everything else depends or revolve on it, including the *user interface* and *infrastructure components* such as the database or data provider.  
Another way of saying it is that **dependencies should always go from the outside of the hexagon to the** inside, as we can see in the schema below:

![Alt text](readme-resources/schema_hexagon.png?raw=true "Schema hexagon")

While it is often not obvious how to apply this principle to mobile applications where the domain layer is generally kept very thin, it still a good exercice to help thinking & structuring code in a modular, **business-centric** way.

Domain contains domain model objects of course but also:  
 
* **APIs** that will be consumed / drived, in our case by a GUI -but it could also be used by console- (schematically, everything residing on the left of the hexagon)  
* **SPIs** that serves to communicate with the oustide world (schematically, everything residing on the right of the hexagon)

It is that *dependency inversion* application that keeps the domain decoupled.  

__Note__: UI is not considered "domain material" as business logic shouldn't be related to the way data is displayed. 

To correlate theory with practice we can look at the project package `artistdetails` and its contents:

![Alt text](readme-resources/structure_artist_details.png?raw=true "Structure artist details")

* `domain` package:
	* Domain data classes `DetailsArtist` and `Release` respectively representing a detailed artist and a release information;
	* A port `ArtistDetailsRemoteLoader` to fetch artist details from the instructure.
* `use-case` package:		 
	* A port `ArtistDetailsLoader` that will be consumed by the GUI to load the artist details.
* `ui` package:
	* A view model `ArtistDetailsViewModel` that drives the domain by using the port `ArtistDetailsLoader`, transforms domain objects into displayable data and exposes this data;
	* A fragment `ArtistDetailsFragment` that will observe and display the displayable data from `ArtistDetailsViewModel`.
* `infra` package:
	* `MBArtistDetailsLoader` which implements the port Port `ArtistDetailsRemoteLoader` using the MusicBrainz API.
* `di` package:
   * `ArtistDetailsModule` is the dependency resolver.
		
### Enforcing the hexagon boundaries

Important infrastructure libraries used in the projet such as *Retrofit* and *Realm* are abstracted away from the domain which never uses them directly, thus avoiding to be polluted with non-agnostic elements (such as a Realm's `context` and other properties) and allowing an easier library replacement if necessary.

Regarding Retrofit, the abstraction solely consists in the additional interface `ArtistDetailsRemoteLoader` above `MBArtistAPI`.
In the case of Realm, this is a bit more complexe because I didn't want `RealmResults` to leak outside of the infrastructure. That is the reason why I introduced `RealmResultsLiveData`	which, as its name suggests, is a `LiveData` wrapper around `RealmResults`.

### Moving from the hexagon a little bit

In an ideal world, the domain is supposed to be dependant of nothing except the language itself of course & arguably that language's standard library. If we make this rule a hard rule, domain shouldn't depend on anything related to the Android SDK. 
Nevertheless, **I did made a compromise by using architecture component `LiveData`**. In my opinion, it's a pretty solid abstraction (although not being a fan of how it's tied up to lifecycle) and it's easily composable. I can see becoming it a "de facto" standard for the Android plateform.

### Avoiding Rx

Speeking of observable data, I ruled *Rx* out since I felt the conjonction of Kotlin's **coroutines** & `LiveData` would allow to achieve the same goals.
For sure the throttling of the search text would have been more concisely written with Rx but why not avoiding such a large and complex third-party library if possible ?

### Avoiding Data binding

Regarding architecture components, I also choosed to avoid *Data Binding* because I dislike having data-related stuff mixed up in what could have be a **plain and stupid view**. Not to mention the annoying back and forth switch between the code and the layout editor.

### Keeping database & remote model objects at bay

I choosed to use **dedicated domain objects** instead of using Realm or MusicBrainz objects. While it certainly as a cost in terms of mapping, this allows **independance from the infrastructure** (yet again...) and **refined domain representation**. 
Indeed, backend and database objects might returns useless data, might use non-precise types, or might be structured in a way that do no represent key domain abstractions.

In my experience, such a decision as to be contrasted: if the backend is at our hand and allows us to modelize exactly what we want, the domain object could be avoided. Regarding the database, industry major changes like the introduction of "recent" alternatives to historical libraries (*CoreData* or pure *SQLite*) such as Realm, or the  brutal shut down of up and coming services such as *Parse* show us the importance of the abstraction. 

### Choosing Realm over Room

A big advantage,of using Realm in my book is the fact that it is **multi-platform** that why I tend to use it for applications that runs on both iOS & Android. The modeling effort can be mutualized as well as the DAO interfaces.
On the other-side, one advantage of *Room* is its out-of-the-box fit with `LiveData`. Should I have opted for Room, I wouldn't have had to make a wrapper for it.

Anyway I'm sure Room would have been an excellent fit for the local database as well but I do not know much about it yet while I do know Realm so it made the choice easier.

__Note__: The database is not encrypted as no sensitive nor personal data is stored. But Realm provides a very simple encryption mechanism so all we would need to do would be to create an encryption key for instance with the KeyStore API.

## Less significant decisions

* I did not use the complete MusicBrainz API url as the base Retrofit url. This allows to use the wikipedia extract url MusicBrainz that does not seems to be available through the API. Also this could allow to use the same Retrofit instance and have more control of which version to use for each one of the API.

* In order to keep thing simple, I deliberately choosed to share the `ArtistDetailsViewModel` with all artist details fragments rather than using a dedicated view model for each. It would clearly require more separation would the number of tabs or their intrinsic complexity grows.


