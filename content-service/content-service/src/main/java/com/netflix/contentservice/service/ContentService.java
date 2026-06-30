package com.netflix.contentservice.service;


import com.netflix.contentservice.dto.MovieRequest;
import com.netflix.contentservice.dto.MovieResponse;
import com.netflix.contentservice.model.Genre;
import com.netflix.contentservice.model.Movie;
import com.netflix.contentservice.model.VideoStatus;
import com.netflix.contentservice.repository.MovieRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final MovieRespository movieRespository;

    /**
     * Add a new movie to the catalog
     * Video is not uploaded yet at this stage
     */

    public MovieResponse addMovie(MovieRequest request){

        log.info("Adding new movie: {}",request.getTitle());

        Movie movie=new Movie();

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenre(request.getGenre());
        movie.setDirector(request.getDirector());
        movie.setCast(request.getCast());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setRating(request.getRating());
        movie.setTumbnailUrl(request.getTumbnailUrl());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setVideoStatus(VideoStatus.PENDING);

        Movie savedMovie=movieRespository.save(movie);
        log.info("Movie added wiht ID: {}" ,savedMovie.getId());

        return mapToResponse(savedMovie);

    }

    /*
    *Get all the movies in Catalog
     */
    public List<MovieResponse> getAllMovies(){
        return movieRespository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get movie bye ID

     */

    public MovieResponse getMoviesById(String movieId){
        Movie movie=movieRespository.findById(movieId)
                .orElseThrow(()->new RuntimeException("Movie not found: "+movieId));

        return mapToResponse(movie);
    }

    /**
     * get movie by genre

     */

    public List<MovieResponse> getMoviesByGenre(Genre genre )
    {
        return movieRespository.findByGenre(genre)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /*
    *Search movies by title
     */

    public List<MovieResponse> searchMovies(String title)
    {
        return movieRespository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void updateVideoKey(String movieId,String videoKey){
        log.info("Updating videokey for movie: {}",movieId);
        Movie movie=movieRespository.findById(movieId)
                .orElseThrow(()->new RuntimeException("Movie not found: "+movieId));

        movie.setVideoKey(videoKey);
        movie.setVideoStatus(VideoStatus.UPLOADED);
        movieRespository.save(movie);

    }

    public void updateHlsUrl(String movieId,String hlsUrl){
        log.info("updating HLS Url for movie : {} ",movieId);
        Movie movie=movieRespository.findById(movieId)
                .orElseThrow(()->new RuntimeException("Movie not found: "+movieId));
        movie.setHlsUrl(hlsUrl);
        movie.setVideoStatus(VideoStatus.READY);
        movieRespository.save(movie);

        log.info("Movie {} is not ready for Streaming",movieId);

    }

    public void updateVideoStatus(String movieId, VideoStatus status){
        Movie movie = movieRespository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not Found: "+ movieId));
        movie.setVideoStatus(status);
        movieRespository.save(movie);
    }


    private MovieResponse mapToResponse(Movie movie)
    {
        MovieResponse response=new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDescription(movie.getDescription());
        response.setGenre(movie.getGenre());
        response.setDirector(movie.getDirector());
        response.setCast(movie.getCast());
        response.setReleaseYear(movie.getReleaseYear());
        response.setRating(movie.getRating());
        response.setTumbnailUrl(movie.getTumbnailUrl());
        response.setDurationMinutes(movie.getDurationMinutes());
        response.setVideoKey(movie.getVideoKey());
        response.setVideoStatus(movie.getVideoStatus());
        response.setHlsUrl(movie.getHlsUrl());
        response.setCreatedAt(movie.getCreatedAt());

        return response;

    }

}
