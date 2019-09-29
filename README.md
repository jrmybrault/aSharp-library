# aSharp-library

![Alt text](readme-resources/app_icon.png?raw=true "App icon")

## TL;DR

Features: paginated search; deletable previous searches; details tabs; tablet declination

Architecture choices: hexagonal approach; `LiveData`; coroutines; popular third-party libaries

Difficulties: some struggles with API 19; `FragmentPagerAdatper`; testing 

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
* The discography is grouped by `primary type`, although there a not many artists that have anything else than albums ("Pink" has some singles)
* On tablet, discography will be displayed as grid instead of a list, with a dynamic number of items by line (notably more items will be displayed on a line in landscape than in portrait)
* An about tab displays sections of information such as the gender, the date of birth / foundation, the date of death / dissolution, the country, the legal IPI and ISNI codes, the overall rating, a wikipedia extract and a "learn more" button that will open a navigator query with the artist name.
* On tablet the about tab sections are organized slightly differently in order to use the space more efficiently

## Architecture

I like Martin Folwer & Ralph Johnson vision of what *architecture* means. It could be summarized in "the **important stuff**, whatever that is" or "every **significant decisions** made so far". 

Here are the important choices that I made during the test.

### Using the "hexagon" approach

In an *hexagonal* architecture -also known as *ports & adapters*-, the *domain* (core objects model & business logic) is decoupled from the rest of the system : it depends on nothing and everything else depends or revolve on it, including the *user interface* and *infrastructure components* such as the database or data provider.  
Another way of saying it is that **dependencies should always go from the outside of the hexagon to the** inside, as we can see in the schema below:

![Alt text](readme-resources/schema_hexagon.png?raw=true "Schema hexagon")

While it is often not obvious how to apply this principle to mobile applications where the domain layer is generally kept very thin, it still a good exercice to help thinking & structuring code in a modular, **business-centric** way.

Domain contains domain objects model of course but also:  
 
* **APIs** that will be consumed / drived, in our case by a GUI -but it could also be used by console- (schematically, everything residing on the left of the hexagon)  
* **SPIs** that serves to communicate with the oustide world (schematically, everything residing on the right of the hexagon)

It is that *dependency inversion* application that keeps the domain decoupled.  

__Note__: UI is not considered "domain material" as business logic shouldn't be related to the way data is displayed. 

### Multi-modules approach

I'm a big believer in **mutli-modules** approach. This forces us to **think about the system dependencies onward**, enforcing good practices regarding decoupling and testing. Not to mention the possible compilation optimizations this provides.

You will find the project is splitted into 4 modules:

* `:app`: this is obviously the module containing the entry point of the application;
* `:coredomain`: this contains all the domain objects model as well as the *APIs* and *SPIs* interfaces;
* `:musicbrainz`: this contains all MusicBrainz-related objects model and APIs;
* `:sharedfoundation`: this contains some utility classes and functions that have no direct correlation with the project.

Now to correlate the hexagon theory with practice. We can look at how the search artist details feature fits into the project hierarchy:

![Alt text](readme-resources/hexagon_artistdetails_inside.png?raw=true "Artist details inside") 
![Alt text](readme-resources/hexagon_artistdetails_outside.png?raw=true "Artist details outside")

* `com.jbr.coredomain.artistdetails` package:
	* Domain data classes `DetailsArtist` and `Release` respectively representing a detailed artist and a release information;
	* A port `ArtistDetailsLoader` that will be consumed by the GUI to load the artist details.
	* A port `ArtistDetailsRemoteLoader` to fetch artist details from the instructure. 
* `ui` package:
	* A view model `ArtistDetailsViewModel` that drives the domain by using the port `ArtistDetailsLoader`, transforms domain objects into displayable data and exposes this data;
	* A fragment `ArtistDetailsFragment` that will observe and display the displayable data from `ArtistDetailsViewModel`.
* `infra` package:
	* `MBArtistDetailsLoader` which implements the port Port `ArtistDetailsRemoteLoader` using the MusicBrainz API.
* `di` package:
   * `ArtistDetailsModule` is the dependency resolver.
	
__Note__: In the case of this little project, the port `ArtistDetailsRemoteLoader` re-implements the `ArtistDetailsLoader` port because their abstraction is pretty-much equivalent but they could very well drift apart if future requirements make it necessary.

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

### Keeping database & remote objects model at bay

I choosed to use **dedicated domain objects** instead of using Realm or MusicBrainz objects. While it certainly as a cost in terms of mapping, this allows **independance from the infrastructure** (yet again...) and **refined domain representation**. 
Indeed, backend and database objects might returns useless data, might use non-precise types, or might be structured in a way that do no represent key domain abstractions.

In my experience, such a decision as to be contrasted: if the backend is at our hand and allows us to modelize exactly what we want, the domain object could be avoided. Regarding the database, industry major changes like the introduction of "recent" alternatives to historical libraries (*CoreData* or pure *SQLite*) such as Realm, or the  brutal shut down of up and coming services such as *Parse* show us the importance of the abstraction. 

### Choosing Realm over Room

A big advantage,of using Realm in my book is the fact that it is **multi-platform** that why I tend to use it for applications that runs on both iOS & Android. The modeling effort can be mutualized as well as the DAO interfaces.
On the other-side, one advantage of *Room* is its out-of-the-box fit with `LiveData`. Should I have opted for Room, I wouldn't have had to make a wrapper for it.

Anyway I'm sure Room would have been an excellent fit for the local database as well but I do not know much about it yet while I do know Realm so it made the choice easier.

__Note__: The database is not encrypted as no sensitive nor personal data is stored. But Realm provides a very simple encryption mechanism so all we would need to do would be to create an encryption key for instance with the KeyStore API.
 
### Less significant decisions

* Project dependencies are managed with the Gradle Kotlin DSL. Altough the project does not have several module at the time which make it more easy to declare versions. It would also helps if several modules . All the dependencies versions are declared in the Dependencies.kt file in buildSrc.

* I did not use the complete MusicBrainz API url as the base Retrofit url. This allows to use the wikipedia extract url MusicBrainz that does not seems to be available through the API. Also this could allow to use the same Retrofit instance and have more control of which version to use for each one of the API.

* In order to keep thing simple, I deliberately choosed to share the `ArtistDetailsViewModel` with all artist details fragments rather than using a dedicated view model for each. It would clearly require more separation would the number of tabs or their intrinsic complexity grows.

* While struggling to make unit tests work, I faced the decision of whether or not I should open classes to be able to mock them. I finally choosed to rely on *Dexopener* that is able to re-open final classes at runtime, at the cost of a bit of configuration. While being advertised by the *Mockk* library, I wasn't so happy about introducing a dependency to such an unnapreciated (93 stars on Github at the time). In the end, it's still a better option that opening stuff just for the sake of testing. I just hope this solution become more main-stream or that Mockk or Google propose an alternative.

## Major third-party libraries choices

I choosed to manage dependencies with the Gradle Kotlin DSL which make it more easy to share configuration across modules, notably the dependencies versions.

*Retrofit*: "de-facto" standard for HTTP API calls. Also compatible with *Gson* and coroutines through adapters.

*Gson*: JSON serialization / deserialization. I started with the up and coming *Moshi* during the test but faced an unexplained deserialization issue so I switch back to Gson.

*Glide*: high-performance & highly customizble image downloader. There is a lot of alternatives (*Fresco*, *Picasso*, …) out there but I sticked with the one I was the more comfortable with.

*Mockito / Mockk*: popular mocking. I started with Mockito, knowing it well, then I faced the issue of mocking final classes. A lot of people were suggesting using Mockk so I gave it a try and find it covers pretty much all use-cases I need from Mockito. It is still less popular than Mockito but given its API seems more Kotlin idomatic, it might changed at some point. Anyway their usage its pretty close so I don't take too much work to migrate from one to another.

*Koin*: dependency injection. At first I was hesitating to use Koin because I only have used *Dagger* in the past. Nevertheless given than I literally hated the Dagger experience, I made the move to Koin and didn't regret it at all. It's small, simple (at least for small projects like this one) and it works as expected.

## Additional tools

*Postman*: to try out the MusicBrainz API

*https://www.materialpalette.com*: to define a color palette for the application

## Difficulties & regrets

### Learning curve

Working on a legacy Java project for the last couple years, I've seen this test as an opportunity to learn more on Kotlin and architecture components, that I never had the opportunity to really work with other than while following Google's codelabs. I never thought this would be such an hard task and if I had to doing it again, I would clearly considered using the "old" MVP approach (also, for another reason explained below).

### Struggle with MusicBrainz API

MusicBrainz API works just fine but the documentation is laking. I searched over and over where I could get a precise model description, that would precise which field is mandatory or not. Instead of that, I've modelized a lot of properties that I genuinely thought were mandatory only to realize -while using the application- they were not. In short, I've would appreciated if MusicBrainz provided a Swagger description file or a Postman collection.

### Struggle with API 19

I struggled with an API 19 incompatiblity that was causing a crash at launch. It was revolving around Retrofit and the `SSLSocketFactory`. Further investigation showed that is was rather the *OkHttp* library (a transitive dependency of Retrofit) version that was incompatible with API 19. I downgraded Retrofit version to a compatible one but it was still crashing. I then waste a lot of time trying to add boilerplate code (custom SSLSocketFactory or forcing an update through *Google Play Services*, as suggested on the internet) to make it work to finally realize the problem was coming from the Retrofit loggin adapter library and not Retrofit itself.

### Struggle with `FragmentPagerAdapter` 

I spend quite some time trying to fix a strange bug: when selecting an artist to see the details screen then coming back to the search screen and then finally selecting an artist again, the discography and about tabs only displayed blank content. Through debuging and logging, I detected that the lifecycle of both fragments was weird without understand why. The documentation of `FragmentPagerAdapter` didn't help me at all. After trying a multitude of different ways to instantiate those fragments or to load their contents, I almost gave up before trying, without much hope, to use a `FragmentStatePagerAdatper` instead. Fortunately it worked, even though I didn't really spent more time understand why.

### Struggle with tests

I can't say if was a surprise, having already experienced it in the past, but the tests gave me a lot of headaches, especially instrumented ones.   

Notably, while testing `SearchArtistFragment` I faced the issue of unmockable final classes. As already explained, I didn't want to open everything just for testing, and at first, I didn't want to use *Dexopener* either.  
So my reflex was to hide the `SearchArtistViewModel` behind an interface, as I would have done using an *MVP* approach. The fragment only knowing that interface, that would have allow me to mock it easily for the tests. But then *Koin* was in the way because viewModel() DSL expect an instance of `ViewModel`. But getting rid of Koin wouldn't solve the problem either because as a matter of fact this only reflect a bigger issue with architecture components: `ViewModelProvider` doesn't allow the use of an interface. In my opinion, this is a real design flaw and if I the motivation behind it (from my understanding: simplify the life of the developpers especially regarding the lifecycle) is noble, it clearly stand in the way of decoupling and it only makes tests harder.

I also couldn't find a proper way to test:

* `FoundArtistsListAdapter` because I didn't find a way for the view holder `itemView` to be mocked
* `MBArtistFinder` because I faced issue with the conjection of `LiveData` and coroutines
* Some functions of `RealmPreviousArtistSearchesDao` that requires Realm to be instantiated with a Looper and I didn't found a way to bypass this

Anyway, I let thoses 3 unfinished test classes in the project just for the sake of the discussion.

### Should-haves (but no more time :'()

* Ideally, the `searchartist` and `artistdetails` should have been packaged into separated modules, along with their respective tests. This would have allows to make a lot of things `internal` instead of `public` thus being sure there will be no future coupling between. 
* Manage no available network
* Manage networking errors
* Add more tests

