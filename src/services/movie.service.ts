import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { Movie, Star, Genre } from '../models/movie.model';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private genres: Genre[] = [
    { id: 1, name: 'Action' }, { id: 2, name: 'Sci-Fi' }, { id: 3, name: 'Drama' },
    { id: 4, name: 'Comedy' }, { id: 5, name: 'Thriller' }, { id: 6, name: 'Adventure' },
    { id: 7, name: 'Fantasy' }, { id: 8, name: 'Crime' }, { id: 9, name: 'Animation' },
    { id: 10, name: 'Horror' }
  ];

  private stars: Star[] = [
    { id: 's1', name: 'Keanu Reeves', birthYear: 1964, photoUrl: 'https://picsum.photos/seed/keanu/200/300' },
    { id: 's2', name: 'Laurence Fishburne', birthYear: 1961, photoUrl: 'https://picsum.photos/seed/laurence/200/300' },
    { id: 's3', name: 'Carrie-Anne Moss', birthYear: 1967, photoUrl: 'https://picsum.photos/seed/carrie/200/300' },
    { id: 's4', name: 'Harrison Ford', birthYear: 1942, photoUrl: 'https://picsum.photos/seed/harrison/200/300' },
    { id: 's5', name: 'Mark Hamill', birthYear: 1951, photoUrl: 'https://picsum.photos/seed/mark/200/300' },
    { id: 's6', name: 'Carrie Fisher', birthYear: 1956, photoUrl: 'https://picsum.photos/seed/fisher/200/300' },
    { id: 's7', name: 'Leonardo DiCaprio', birthYear: 1974, photoUrl: 'https://picsum.photos/seed/leo/200/300' },
    { id: 's8', name: 'Joseph Gordon-Levitt', birthYear: 1981, photoUrl: 'https://picsum.photos/seed/joseph/200/300' },
    { id: 's9', name: 'Elliot Page', birthYear: 1987, photoUrl: 'https://picsum.photos/seed/elliot/200/300' },
    { id: 's10', name: 'Tom Hanks', birthYear: 1956, photoUrl: 'https://picsum.photos/seed/hanks/200/300' },
    { id: 's11', name: 'Tim Allen', birthYear: 1953, photoUrl: 'https://picsum.photos/seed/allen/200/300' },
  ];

  private movies: Movie[] = [
    { id: 'm1', title: 'The Matrix', year: 1999, director: 'Wachowskis', genres: [this.genres[0], this.genres[1]], stars: [this.stars[0], this.stars[1], this.stars[2]], rating: 8.7, posterUrl: 'https://picsum.photos/seed/matrix/400/600', description: 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.' },
    { id: 'm2', title: 'Star Wars: A New Hope', year: 1977, director: 'George Lucas', genres: [this.genres[1], this.genres[5], this.genres[6]], stars: [this.stars[3], this.stars[4], this.stars[5]], rating: 8.6, posterUrl: 'https://picsum.photos/seed/starwars/400/600', description: 'Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire\'s world-destroying battle station.' },
    { id: 'm3', title: 'Inception', year: 2010, director: 'Christopher Nolan', genres: [this.genres[0], this.genres[1], this.genres[5]], stars: [this.stars[6], this.stars[7], this.stars[8]], rating: 8.8, posterUrl: 'https://picsum.photos/seed/inception/400/600', description: 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.' },
    { id: 'm4', title: 'Forrest Gump', year: 1994, director: 'Robert Zemeckis', genres: [this.genres[2], this.genres[3]], stars: [this.stars[9]], rating: 8.8, posterUrl: 'https://picsum.photos/seed/forrest/400/600', description: 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.' },
    { id: 'm5', title: 'Toy Story', year: 1995, director: 'John Lasseter', genres: [this.genres[8], this.genres[3], this.genres[5]], stars: [this.stars[9], this.stars[10]], rating: 8.3, posterUrl: 'https://picsum.photos/seed/toystory/400/600', description: 'A cowboy doll is profoundly threatened and jealous when a new spaceman figure supplants him as top toy in a boy\'s room.' },
    // Add more movies to reach at least 25 for pagination
    { id: 'm6', title: 'Pulp Fiction', year: 1994, director: 'Quentin Tarantino', genres: [this.genres[2], this.genres[7]], stars: [], rating: 8.9, posterUrl: 'https://picsum.photos/seed/pulp/400/600', description: 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.' },
    { id: 'm7', title: 'The Dark Knight', year: 2008, director: 'Christopher Nolan', genres: [this.genres[0], this.genres[7], this.genres[2]], stars: [], rating: 9.0, posterUrl: 'https://picsum.photos/seed/darkknight/400/600', description: 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.' },
    { id: 'm8', title: 'Fight Club', year: 1999, director: 'David Fincher', genres: [this.genres[2]], stars: [], rating: 8.8, posterUrl: 'https://picsum.photos/seed/fightclub/400/600', description: 'An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more.' },
    { id: 'm9', title: 'Goodfellas', year: 1990, director: 'Martin Scorsese', genres: [this.genres[2], this.genres[7]], stars: [], rating: 8.7, posterUrl: 'https://picsum.photos/seed/goodfellas/400/600', description: 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.' },
    { id: 'm10', title: 'The Shawshank Redemption', year: 1994, director: 'Frank Darabont', genres: [this.genres[2]], stars: [], rating: 9.3, posterUrl: 'https://picsum.photos/seed/shawshank/400/600', description: 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.' },
    { id: 'm11', title: 'The Godfather', year: 1972, director: 'Francis Ford Coppola', genres: [this.genres[2], this.genres[7]], stars: [], rating: 9.2, posterUrl: 'https://picsum.photos/seed/godfather/400/600', description: 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.' },
    { id: 'm12', title: 'Spirited Away', year: 2001, director: 'Hayao Miyazaki', genres: [this.genres[8], this.genres[6], this.genres[5]], stars: [], rating: 8.6, posterUrl: 'https://picsum.photos/seed/spirited/400/600', description: 'During her family\'s move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.' },
    { id: 'm13', title: 'Parasite', year: 2019, director: 'Bong Joon Ho', genres: [this.genres[4], this.genres[2], this.genres[4]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/parasite/400/600', description: 'Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.' },
    { id: 'm14', title: 'Interstellar', year: 2014, director: 'Christopher Nolan', genres: [this.genres[1], this.genres[2], this.genres[5]], stars: [], rating: 8.6, posterUrl: 'https://picsum.photos/seed/interstellar/400/600', description: 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.' },
    { id: 'm15', title: 'Gladiator', year: 2000, director: 'Ridley Scott', genres: [this.genres[0], this.genres[2], this.genres[5]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/gladiator/400/600', description: 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.' },
    { id: 'm16', title: 'The Lion King', year: 1994, director: 'Roger Allers, Rob Minkoff', genres: [this.genres[8], this.genres[2], this.genres[5]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/lionking/400/600', description: 'Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.' },
    { id: 'm17', title: 'Back to the Future', year: 1985, director: 'Robert Zemeckis', genres: [this.genres[1], this.genres[3], this.genres[5]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/bttf/400/600', description: 'Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.' },
    { id: 'm18', title: 'Psycho', year: 1960, director: 'Alfred Hitchcock', genres: [this.genres[4], this.genres[9]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/psycho/400/600', description: 'A Phoenix secretary embezzles $40,000 from her employer\'s client, goes on the run, and checks into a remote motel run by a young man under the domination of his mother.' },
    { id: 'm19', title: 'Alien', year: 1979, director: 'Ridley Scott', genres: [this.genres[1], this.genres[9]], stars: [], rating: 8.4, posterUrl: 'https://picsum.photos/seed/alien/400/600', description: 'After a space merchant vessel receives an unknown transmission as a distress call, one of the crew is attacked by a mysterious life form and they soon realize that its life cycle has merely begun.' },
    { id: 'm20', title: 'Whiplash', year: 2014, director: 'Damien Chazelle', genres: [this.genres[2]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/whiplash/400/600', description: 'A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student\'s potential.' },
    { id: 'm21', title: 'The Departed', year: 2006, director: 'Martin Scorsese', genres: [this.genres[2], this.genres[7], this.genres[4]], stars: [this.stars[6]], rating: 8.5, posterUrl: 'https://picsum.photos/seed/departed/400/600', description: 'An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.' },
    { id: 'm22', title: 'The Prestige', year: 2006, director: 'Christopher Nolan', genres: [this.genres[2], this.genres[4], this.genres[1]], stars: [], rating: 8.5, posterUrl: 'https://picsum.photos/seed/prestige/400/600', description: 'After a tragic accident, two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.' },
    { id: 'm23', title: 'Saving Private Ryan', year: 1998, director: 'Steven Spielberg', genres: [this.genres[0], this.genres[2]], stars: [this.stars[9]], rating: 8.6, posterUrl: 'https://picsum.photos/seed/ryan/400/600', description: 'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.' },
    { id: 'm24', title: 'A Clockwork Orange', year: 1971, director: 'Stanley Kubrick', genres: [this.genres[1], this.genres[2], this.genres[7]], stars: [], rating: 8.3, posterUrl: 'https://picsum.photos/seed/clockwork/400/600', description: 'In the future, a sadistic gang leader is imprisoned and volunteers for a conduct-aversion experiment, but it doesn\'t go as planned.' },
    { id: 'm25', title: 'Reservoir Dogs', year: 1992, director: 'Quentin Tarantino', genres: [this.genres[2], this.genres[7]], stars: [this.stars[1]], rating: 8.3, posterUrl: 'https://picsum.photos/seed/dogs/400/600', description: 'When a simple jewelry heist goes horribly wrong, the surviving criminals begin to suspect that one of them is a police informant.' },
  ];
  
  private readonly SIMULATED_DELAY = 300; // ms

  getMovies(
    page: number = 1,
    limit: number = 20,
    sortBy: 'title' | 'rating' = 'rating',
    sortOrder: 'asc' | 'desc' = 'desc',
    title: string | null = null,
    genre: string | null = null,
    firstLetter: string | null = null
  ): Observable<{ movies: Movie[], total: number }> {
    let filteredMovies = [...this.movies];

    if (title) {
        filteredMovies = filteredMovies.filter(m => m.title.toLowerCase().includes(title.toLowerCase()));
    }
    if (genre) {
        filteredMovies = filteredMovies.filter(m => m.genres.some(g => g.name === genre));
    }
    if (firstLetter) {
        filteredMovies = filteredMovies.filter(m => m.title.toLowerCase().startsWith(firstLetter.toLowerCase()));
    }

    filteredMovies.sort((a, b) => {
        if (sortBy === 'title') {
            return sortOrder === 'asc' ? a.title.localeCompare(b.title) : b.title.localeCompare(a.title);
        } else { // rating
            return sortOrder === 'asc' ? a.rating - b.rating : b.rating - a.rating;
        }
    });

    const total = filteredMovies.length;
    const start = (page - 1) * limit;
    const end = start + limit;
    const paginatedMovies = filteredMovies.slice(start, end);

    return of({ movies: paginatedMovies, total }).pipe(delay(this.SIMULATED_DELAY));
  }

  getMovieById(id: string): Observable<Movie | undefined> {
    const movie = this.movies.find((m) => m.id === id);
    return of(movie).pipe(delay(this.SIMULATED_DELAY));
  }

  getStarById(id: string): Observable<Star | undefined> {
    const star = this.stars.find((s) => s.id === id);
    return of(star).pipe(delay(this.SIMULATED_DELAY));
  }

  getMoviesByStarId(starId: string): Observable<Movie[]> {
    const starMovies = this.movies.filter(m => m.stars.some(s => s.id === starId));
    return of(starMovies).pipe(delay(this.SIMULATED_DELAY));
  }
  
  getGenres(): Observable<Genre[]> {
    return of(this.genres).pipe(delay(this.SIMULATED_DELAY / 2));
  }
}
