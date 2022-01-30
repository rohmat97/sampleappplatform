package com.example.sampleappplatform.datamodule.module.repository

import com.example.sampleappplatform.datamodule.repository.room.RoomRepository
import com.example.sampleappplatform.datamodule.repository.room.RoomRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<RoomRepository> { RoomRepositoryImpl(roomService = get()) }
}