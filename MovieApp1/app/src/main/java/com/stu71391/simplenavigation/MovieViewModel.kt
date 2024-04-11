package com.stu71391.simplenavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71391.simplenavigation.R
import com.stu71391.simplenavigation.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MovieViewModel : ViewModel() {

    /*It is not necessary to define the max Seats because it
      is already generated when instantiating the class*/
    val movies = listOf(
        Movie(
            id = 1,
            title = "ABIGAIL",
            description = "After a group of would-be criminals kidnap the 12-year-old ballerina daughter of a powerful underworld figure, all they have to do to collect a \$50 million ransom is watch the girl overnight. In an isolated mansion, the captors start to dwindle, one by one, and they discover, to their mounting horror, that they’re locked inside with no normal little girl.",
            runningTime = "N/A",
            starring = "Dan Stevens, Giancarlo Esposito, Kathryn Newton, Kevin Durand, Alisha Weir",
            posterResId = R.drawable.pt_abigail
        ),
        Movie(
            id = 2,
            title = "SEIZE THEM!",
            description = "Dark age Britain where Queen Dagan is toppled by a revolution led by Humble Joan. The Queen becomes a fugitive in her own land and must face hardship and danger as she embarks on a voyage to win back her throne.",
            runningTime = "1hr 31mins",
            starring = "Nick Frost, Paul Kaye, Nitin Ganatra, Aimee Lou Wood, Murray McArthur, Nicola Coughlan, Lolly Adefope",
            posterResId = R.drawable.pt_seize_them
        ),
        Movie(
            id = 3,
            title = "MOTHERS’ INSTINCT",
            description = "Starring Academy Award winners Jessica Chastain and Anne Hathaway, Mothers’ Instinct is an unnerving psychological thriller about two best friends and neighbors, Alice and Céline, whose perfect lives in ‘60s suburbia are shattered by a tragic accident involving one of their children. Marking the directorial debut of acclaimed cinematographer Benoit Delhomme, we follow Alice and Céline as their familial bonds are gradually undermined by guilt and paranoia and a gripping battle of wills develops, revealing the darker side of maternal love.",
            runningTime = "1hr 34mins",
            starring = "Jessica Chastain, Anne Hathaway",
            posterResId = R.drawable.pt_mothers_instinct
        ),
        Movie(
            id = 4,
            title = "IO CAPITANO",
            description = "ave Dakar to travel to Europe where they believe opportunities await. On a journey neither could have imagined, the boys face the dangers and the beauty of the desert, the shock of detention centres in Libya, and the perils of the sea in their pursuit of a better life, in an epic story that offers a deeply human perspective on the migrant crisis.\n" +
                    "\n" +
                    "\n" +
                    "Directed by two-time BAFTA-nominee Matteo Garrone (Gomorrah, Tale of Tales), IO CAPITANO has been nominated for Best International Feature at the Academy Awards, and won the Silver Lion and Best Young Actor Awards at Venice Film Festival.\n" +
                    "\n",
            runningTime = "2hr 1min",
            starring = "Bamar Kane, Seydou Sarr, Didier Njikam, Moustapha Fall",
            posterResId = R.drawable.pt_io_capitano
        ),
        Movie(
            id = 5,
            title = "SUGA | AGUST D TOUR ‘D-DAY’ THE MOVIE",
            description = "The eagerly awaited film of BTS SUGA’s Encore Concert <SUGA│Agust D TOUR ‘D-DAY’ THE MOVIE> bursts onto the big screen worldwide, with a behind the scenes look of what made his concert a worldwide phenomenon, featuring never before seen footage!\n" +
                    "\n" +
                    "As the grand finale of the world tour, “SUGA | Agust D TOUR ‘D-DAY’ THE FINAL” marked the culmination of 25 concerts held in 10 cities, which captivated a total audience of 290,000 throughout its run. \n" +
                    "\n" +
                    "Experience the pulsating energy and excitement of “D-DAY’ THE FINAL” on screen, everything from the exquisite sounds traversing the boundary between “21st Century Pop Icon” BTS member SUGA and solo artist Agust D, electrifying performance, explosive energy, to special duet stages featuring fellow BTS members RM, Jimin, and Jung Kook.",
            runningTime = "1hr 24mins",
            starring = "SUGA",
            posterResId = R.drawable.pt_suga
        ),
        Movie(
            id = 6,
            title = "WICKED LITTLE LETTERS",
            description = "A 1920s English seaside town bears witness to a dark and absurd scandal in this riotous mystery comedy. Based on a stranger than fiction true story, WICKED LITTLE LETTERS follows two neighbours: deeply conservative local Edith Swan (Olivia Colman) and rowdy Irish migrant Rose Gooding (Jessie Buckley). When Edith and fellow residents begin to receive wicked letters full of unintentionally hilarious profanities, foulmouthed Rose is charged with the crime. The anonymous letters prompt a national uproar, and a trial ensues. However, as the town's women - led by Police Officer Gladys Moss (Anjana Vasan) - begin to investigate the crime themselves, they suspect that something is amiss, and Rose may not be the culprit after all.",
            runningTime = "1hr 40mins",
            starring = "Olivia Colman, Timothy Spall, Eileen Atkins, Gemma Jones, Anjana Vasan, Jessie Buckley",
            posterResId = R.drawable.pt_wicked_little_letters
        ),
        Movie(
            id = 7,
            title = "LATE NIGHT WITH THE DEVIL",
            description = "October 31, 1977. Johnny Carson rival Jack Delroy hosts a syndicated late night talk show ‘Night Owls’ that has long been a trusted companion to insomniacs around the country. A year after the tragic death of Jack’s wife, ratings have plummeted. Desperate to turn his fortunes around, Jack plans a Halloween special like no other, unaware that he is about to unleash evil into the living rooms of America.",
            runningTime = "1hr 33mins",
            starring = "David Dastmalchian, Fayssal Bazzi, Laura Gordon, Ian Bliss",
            posterResId = R.drawable.late_night_with_the_devil
        ),
        Movie(
            id = 8,
            title = "LUGA (2021)",
            description = "Set in a beautiful seaside town on the Italian Riviera, the original animated feature is a coming-of-age story about one young boy experiencing an unforgettable summer filled with gelato, pasta and endless scooter rides. Luca shares these adventures with his newfound best friend, but all the fun is threatened by a deeply-held secret: he is a sea monster from another world just below the water’s surface.",
            runningTime = "1hr 31mins",
            starring = "Maya Rudolph, Jacob Tremblay, Jack Dylan Grazer, Emma Berman, Marco Barricelli",
            posterResId = R.drawable.pt_luca
        )

    )

    fun getMovieById(movieId: Int): Movie {
        return movies.find { it.id == movieId }!!
    }

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()
    private val _searchedMovies = MutableStateFlow(movies)
    val searchedMovies = searchText
        .combine(_searchedMovies){ text, movies ->
            when {
                text.isNotEmpty() -> movies.filter { movie ->
                    movie.title.contains(text.trim(), ignoreCase = true)
                }

                else -> emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }
}