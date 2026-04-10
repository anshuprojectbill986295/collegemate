package com.anshu.collegemate.ui.View.Screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM

//See This UI is written fully by AI... Replace with your own code...
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignmentTestDetailedScreen(
    id: String,
    type: String,
    assTestVm: AssignmentTestVM = AssignmentTestVM()
) {
    val context = LocalContext.current
    when (type) {
        "TEST" -> {
            val testItem by assTestVm.testByID.collectAsState()
            LaunchedEffect(Unit) { assTestVm.getTestByID(id) }

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Test",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${testItem.test.subjectName} (${testItem.test.subjectCode})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Syllabus",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = testItem.test.syllabus.ifEmpty { "No syllabus provided" },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Test Date: ${DateTimeUtil.getDateMonthFromLong(testItem.test.testDate)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Created by: ${testItem.test.createdBy} on ${DateTimeUtil.getDateMonthFromLong(testItem.test.createdAt)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (testItem.test.syllabusImageUrl.isNotEmpty()) {
                        Button(
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, testItem.test.syllabusImageUrl.toUri())
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.imagesmode_24px),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("View Syllabus Image")
                        }
                    }
                    if (testItem.test.syllabusFileUrl.isNotEmpty()) {
                        Button(
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, testItem.test.syllabusFileUrl.toUri())
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.attach_file_24px),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("View Syllabus PDF")
                        }
                    }
                    if (testItem.test.syllabusImageUrl.isEmpty() && testItem.test.syllabusFileUrl.isEmpty()) {
                        Text(
                            text = "No attachments",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        "ASSIGNMENT" -> {
            val assItem by assTestVm.assByID.collectAsState()
            LaunchedEffect(Unit) { assTestVm.getAssByID(id) }

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Assignment",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${assItem.assignment.subjectName} (${assItem.assignment.subjectCode})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Question",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = assItem.assignment.questionText.ifEmpty { "No question text provided" },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Due Date: ${DateTimeUtil.getDateMonthFromLong(assItem.assignment.lastDateToSubmit)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Created by: ${assItem.assignment.createdBy} on ${DateTimeUtil.getDateMonthFromLong(assItem.assignment.createdAt)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (assItem.assignment.questionImageUrl.isNotEmpty()) {
                        Button(
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, assItem.assignment.questionImageUrl.toUri())
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.imagesmode_24px),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("View Question Image")
                        }
                    }
                    if (assItem.assignment.questionFileUrl.isNotEmpty()) {
                        Button(
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, assItem.assignment.questionFileUrl.toUri())
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.attach_file_24px),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("View Question PDF")
                        }
                    }
                    if (assItem.assignment.questionImageUrl.isEmpty() && assItem.assignment.questionFileUrl.isEmpty()) {
                        Text(
                            text = "No attachments",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}