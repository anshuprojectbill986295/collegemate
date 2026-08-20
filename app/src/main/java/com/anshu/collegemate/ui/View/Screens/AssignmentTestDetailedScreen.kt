package com.anshu.collegemate.ui.View.Screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import com.anshu.collegemate.ui.theme.CardColors
import com.anshu.collegemate.ui.theme.CardColorsScheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignmentTestDetailedScreen(
    id: String,
    type: String,
    assTestVm: AssignmentTestVM = viewModel()
) {
    val context = LocalContext.current
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xfff0f4f8), Color(0xffe0e7ff)),
        start = Offset(0f, 0f), end = Offset(100f, 100f)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(gradient)) {

        when (type) {
            "TEST" -> {
                val testItem by assTestVm.testByID.collectAsState()
                val cardCS = CardColorsScheme.GREENTHEME
                LaunchedEffect(id) { assTestVm.getTestByID(id) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SubjectHeader(
                            title = "Test",
                            subjectName = testItem.test.subjectName,
                            subjectCode = testItem.test.subjectCode,
                            cardCS = cardCS
                        )
                    }

                    item {
                        DetailSection(
                            header = "Syllabus",
                            content = testItem.test.syllabus,
                            emptyText = "No syllabus provided",
                            cardCS = cardCS
                        )
                    }

                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoTile(
                                icon = Icons.Default.DateRange,
                                label = "Test Date",
                                value = DateTimeUtil.getDateMonthFromLong(testItem.test.testDate),
                                cardCS = cardCS
                            )
                            InfoTile(
                                icon = Icons.Default.Info,
                                label = "Max Marks",
                                value = "${testItem.test.maxMarks} Marks",
                                cardCS = cardCS
                            )
                            InfoTile(
                                icon = Icons.Default.Person,
                                label = "Created By",
                                value = "${testItem.test.createdBy} (${DateTimeUtil.getTimeAgo(testItem.test.createdAt)})",
                                cardCS = cardCS
                            )
                        }
                    }

                    item {
                        AttachmentSection(
                            imageUrl = testItem.test.syllabusImageUrl,
                            fileUrl = testItem.test.syllabusFileUrl,
                            cardCS = cardCS,
                            onOpen = { url ->
                                context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }
            }
            "ASSIGNMENT" -> {
                val assItem by assTestVm.assByID.collectAsState()
                val cardCS = CardColorsScheme.ORANGETHEME
                LaunchedEffect(id) { assTestVm.getAssByID(id) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SubjectHeader(
                            title = "Assignment",
                            subjectName = assItem.assignment.subjectName,
                            subjectCode = assItem.assignment.subjectCode,
                            cardCS = cardCS
                        )
                    }

                    item {
                        DetailSection(
                            header = "Question",
                            content = assItem.assignment.questionText,
                            emptyText = "No question text provided",
                            cardCS = cardCS
                        )
                    }

                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            InfoTile(
                                icon = Icons.Default.DateRange,
                                label = "Due Date",
                                value = DateTimeUtil.getDateMonthFromLong(assItem.assignment.lastDateToSubmit),
                                cardCS = cardCS,
                                iconTint = Color.Red
                            )
                            InfoTile(
                                icon = Icons.Default.Person,
                                label = "Created By",
                                value = "${assItem.assignment.createdBy} (${DateTimeUtil.getTimeAgo(assItem.assignment.createdAt)})",
                                cardCS = cardCS
                            )
                        }
                    }

                    item {
                        AttachmentSection(
                            imageUrl = assItem.assignment.questionImageUrl,
                            fileUrl = assItem.assignment.questionFileUrl,
                            cardCS = cardCS,
                            onOpen = { url ->
                                context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }
            }
        }
    }
}

@Composable
fun SubjectHeader(title: String, subjectName: String, subjectCode: String, cardCS: CardColors) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = Color(cardCS.cardBackgroundColor)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardCS.assignmentWordContainerColor))
            ) {
                Text(
                    text = title.uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = Color(cardCS.assignmentWordContentColor)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = subjectName,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 34.sp,
                color = Color(cardCS.nameColor)
            )
            Text(
                text = subjectCode,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(cardCS.instructorColor)
            )
        }
    }
}

@Composable
fun DetailSection(header: String, content: String, emptyText: String, cardCS: CardColors) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = header,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(cardCS.nameColor)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = content.ifEmpty { emptyText },
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = if (content.isEmpty()) Color.Gray else Color.Black
            )
        }
    }
}

@Composable
fun InfoTile(icon: ImageVector, label: String, value: String, cardCS: CardColors, iconTint: Color? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(cardCS.timingBackgroundColor).copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint ?: Color(cardCS.instructorColor),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun AttachmentSection(imageUrl: String, fileUrl: String, cardCS: CardColors, onOpen: (String) -> Unit) {
    if (imageUrl.isEmpty() && fileUrl.isEmpty()) return

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Attachments",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(cardCS.nameColor)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        if (imageUrl.isNotEmpty()) {
            AttachmentTile(
                iconRes = R.drawable.imagesmode_24px,
                label = "View Question Image",
                cardCS = cardCS,
                onClick = { onOpen(imageUrl) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        if (fileUrl.isNotEmpty()) {
            AttachmentTile(
                iconRes = R.drawable.attach_file_24px,
                label = "View Question PDF",
                cardCS = cardCS,
                onClick = { onOpen(fileUrl) }
            )
        }
    }
}

@Composable
fun AttachmentTile(iconRes: Int, label: String, cardCS: CardColors, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(cardCS.timingBackgroundColor)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color(cardCS.timingContentColor),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(cardCS.timingContentColor)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.arrow_forward_24px),
                contentDescription = null,
                tint = Color(cardCS.timingContentColor),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
