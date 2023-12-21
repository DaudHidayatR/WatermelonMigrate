package com.google.watermelonmigrasi.presentation.bottom_nav.treatment.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.watermelonmigrasi.alarms.ContactEvent
import com.google.watermelonmigrasi.alarms.ContactState
import com.google.watermelonmigrasi.alarms.alarm.Alarm
import com.google.watermelonmigrasi.alarms.alarm.AlarmItem
import com.google.watermelonmigrasi.ui.theme.WatermelonMigrasiTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(navigateBack: () -> Unit, onEvent: (ContactEvent) -> Unit,) {

    WatermelonMigrasiTheme {
        Scaffold(
            topBar = {
                MedicationTopBar(
                    navigateBack = navigateBack
                )
            },
            content = { padding ->
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        ) {
                        var     scheduler: Alarm = Alarm( context =LocalContext.current  )
                        var alarmItem: AlarmItem? = null
                        var obat by remember {
                            mutableStateOf("")
                        }
                        var hour by remember {
                            mutableStateOf(0)
                        }
                        var minute by remember {
                            mutableStateOf(0)
                        }
                        var state: ContactState by remember {
                            mutableStateOf(ContactState())
                        }
                        val dateTime = LocalDateTime.now()

                        val coba = remember {
                            TimePickerState(
                                initialHour = dateTime.hour,
                                initialMinute = dateTime.minute,
                                is24Hour = true
                            )
                        }


                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            TextField(
                                value = state.dosis,
                                onValueChange = {
                                    onEvent(ContactEvent.SetDosis(it))

                                },
                                placeholder = {
                                    Text(text = "Dosis")
                                }
                            )
                            TextField(
                                value = state.obat,
                                onValueChange = {
                                    onEvent(ContactEvent.SetObat(it))
                                    obat = it
                                },
                                placeholder = {
                                    Text(text = "Obat")
                                }
                            )
                            TextField(
                                value = state.jenis,
                                onValueChange = {
                                    onEvent(ContactEvent.SetJenis(it))
                                },
                                placeholder = {
                                    Text(text = "Jenis")
                                }
                            )
                            TimeInput(
                                state = coba,
                                modifier = Modifier.padding(16.dp)
                            )
                            onEvent(ContactEvent.SetHour(coba.hour))
                            onEvent(ContactEvent.SetMinute(coba.minute))
                            hour = coba.hour
                            minute = coba.minute

                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                onEvent(ContactEvent.SaveContact)
                                alarmItem = AlarmItem(
                                    obat = obat,
                                    hour = hour,
                                    minute = minute
                                )
                                alarmItem?.let(scheduler::schedule)
                                obat = ""
                                hour = 0
                                minute = 0
                                navigateBack()
                            }) {
                                Text(text = "Save")
                            }
                        }
                    }
                }
            }
        )
    }
}
@Preview
@Composable
fun MedicationScreenPreview() {
    WatermelonMigrasiTheme {
        MedicationScreen( navigateBack = {  } , onEvent = { })
    }
}