package com.ahmed_apps.data.media.dataSource

import com.ahmed_apps.data.media.model.Media
import com.ahmed_apps.data.user.model.User
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull

class MongoMediaDataSource(
    database: MongoDatabase
) : MediaDataSource {

    private val userCollection =
        database.getCollection<User>("users")

    override suspend fun getMediaByIdAndUser(
        mediaId: Int, email: String
    ): Media? {

        val media = userCollection.find(
            eq(User::email.name, email)
        ).firstOrNull()?.mediaList?.find {
            it.mediaId == mediaId
        }

        return media

    }

    override suspend fun getLikedMediaListOfUser(
        email: String
    ): List<Media>? {

        val likedMediaList = userCollection.find(
            eq(User::email.name, email)
        ).firstOrNull()?.mediaList?.filter {
            it.isLiked
        }

        return likedMediaList
    }

    override suspend fun getBookmarkedMediaListOfUser(
        email: String
    ): List<Media>? {

        val bookmarkedMediaList = userCollection.find(
            eq(User::email.name, email)
        ).firstOrNull()?.mediaList?.filter {
            it.isBookmarked
        }

        return bookmarkedMediaList
    }

    override suspend fun upsertMediaToUser(
        email: String, media: Media
    ): Boolean {

        val user = userCollection.find(
            eq(User::email.name, email)
        ).firstOrNull() ?: return false

        val upsertedMediaList = user.mediaList.map { userMedia ->
            if (userMedia.mediaId == media.mediaId) media else userMedia
        }.toMutableList()

        if (!upsertedMediaList.contains(media)) {
            upsertedMediaList.add(media)
        }

        val query = eq(User::email.name, email)
        val updates = Updates.combine(
            Updates.set(User::mediaList.name, upsertedMediaList)
        )
        val options = UpdateOptions().upsert(true)

        return userCollection.updateOne(
            query, updates, options
        ).wasAcknowledged()

    }

    override suspend fun deleteMediaFromUser(
        email: String, mediaId: Int
    ): Boolean {

        val user = userCollection.find(
            eq(User::email.name, email)
        ).firstOrNull() ?: return false

        val mediaToRemove = user.mediaList.find { media ->
            media.mediaId == mediaId
        }

        if (mediaToRemove != null) {
            user.mediaList.remove(mediaToRemove)
        } else {
            return false
        }

        val query = eq(User::email.name, email)
        val updates = Updates.combine(
            Updates.set(User::mediaList.name, user.mediaList)
        )
        val options = UpdateOptions().upsert(true)

        return userCollection.updateOne(
            query, updates, options
        ).wasAcknowledged()
    }
}














