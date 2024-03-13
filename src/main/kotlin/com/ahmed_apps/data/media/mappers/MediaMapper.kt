package com.ahmed_apps.data.media.mappers

import com.ahmed_apps.data.media.model.Media
import com.ahmed_apps.data.media.model.requests.MediaRequest
import com.ahmed_apps.data.media.model.respond.MediaRespond


fun Media.toMediaRespond(): MediaRespond {
    return MediaRespond(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}

fun MediaRespond.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}

fun MediaRequest.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}







