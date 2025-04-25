package com.example.calender

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate


@Parcelize
data class Task(
    val title: String,
    val date: LocalDate
): Parcelable
