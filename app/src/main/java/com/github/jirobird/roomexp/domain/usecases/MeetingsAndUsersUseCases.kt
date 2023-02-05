package com.github.jirobird.roomexp.domain.usecases

import javax.inject.Inject

data class MeetingsAndUsersUseCases @Inject constructor(
    val getMeetingsCount:GetMeetingsCount,
    val insertMeetingsAndUsers: InsertMeetingWithUsers,
    val getMeetingsWithUsers: GetMeetingsWithUsers,
    val insertUser: InsertUser,
    val getAllUsers: GetAllUsers,
)
