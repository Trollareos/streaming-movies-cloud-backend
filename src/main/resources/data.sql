INSERT INTO movies (code, title, director, production_year, duration_minutes, original_language, rating, seasons, rental_cost)
VALUES
('MOVIE00001', 'Blade Runner', 'Ridley Scott', 1982, 117, 'English', 8.1, 0, 3.99),
('MOVIE00002', 'The Matrix', 'Lana Wachowski', 1999, 136, 'English', 8.7, 0, 4.50),
('SERIES0001', 'Breaking Bad', 'Vince Gilligan', 2008, 49, 'English', 9.5, 5, 5.99)
ON CONFLICT (code) DO NOTHING;

INSERT INTO movie_genres (movie_code, genre)
VALUES
('MOVIE00001', 'SCI_FI'),
('MOVIE00001', 'THRILLER'),
('MOVIE00002', 'ACTION'),
('MOVIE00002', 'SCI_FI'),
('SERIES0001', 'DRAMA'),
('SERIES0001', 'THRILLER')
ON CONFLICT DO NOTHING;
