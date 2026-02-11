export interface Genre {
  id: number;
  name: string;
}

export interface Star {
  id: string;
  name: string;
  birthYear: number | null;
  photoUrl: string;
}

export interface Movie {
  id: string;
  title: string;
  year: number;
  director: string;
  genres: Genre[];
  stars: Star[];
  rating: number;
  posterUrl: string;
  description: string;
}
