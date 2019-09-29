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
* Results are ordered by pertinence score then by sort name
* Results are paginated, scrolling to the end of the list will load the next page
* Result item displays sort name, disambiguated type and a hypothetical list of relevant tags
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
* On tablet the about tab sections are organized just a little bit differently in order to use the space more efficiently

