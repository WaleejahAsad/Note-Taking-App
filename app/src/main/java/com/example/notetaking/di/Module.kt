package com.example.notetaking.di

import android.app.Application
import androidx.room.Room
import com.example.notetaking.data_Layer.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providerDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = application,
            NoteDatabase::class.java,
            name = "note_db.db"
        ).build()
    }

}