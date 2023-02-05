package com.github.jirobird.roomexp.ui.meeting

sealed class MeetingViewModelDatabaseEvents {
    object MeetingViewModelDatabaseEventsWillSave: MeetingViewModelDatabaseEvents()
    object MeetingViewModelDatabaseEventsSaved: MeetingViewModelDatabaseEvents()
}
