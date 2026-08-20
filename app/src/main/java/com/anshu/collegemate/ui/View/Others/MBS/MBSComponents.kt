package com.anshu.collegemate.ui.View.Others.MBS

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.theme.CardColorsScheme

@Composable
fun MBSHeader(
    title: String,
    subtitle: String? = null,
    currentStep: Int,
    totalSteps: Int,
    onDismiss: () -> Unit
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xff667eea), Color(0xff764ba2))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        MBSStepIndicator(currentStep = currentStep, totalSteps = totalSteps)
    }
}

@Composable
fun MBSStepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 1..totalSteps) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (i <= currentStep) Color.White else Color.White.copy(alpha = 0.3f)
                    )
            )
        }
    }
    Text(
        text = "Step $currentStep of $totalSteps",
        color = Color.White.copy(alpha = 0.9f),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun MBSSelectionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEEF2FF)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF6366F1)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
fun MBSSubjectCard(
    subject: ScheduleCardData,
    isCancelled: Boolean = false,
    onClick: () -> Unit
) {
    val cardColorScheme = CardColorsScheme.getCCScheme(subject.colorKey)
    val containerColor = if (isCancelled) Color.LightGray.copy(alpha = 0.3f) else Color(cardColorScheme.cardBackgroundColor)
    val contentAlpha = if (isCancelled) 0.5f else 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(enabled = !isCancelled) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCancelled) 0.dp else 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subject.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(cardColorScheme.nameColor).copy(alpha = contentAlpha),
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(cardColorScheme.timingBackgroundColor).copy(alpha = contentAlpha)
                ) {
                    Text(
                        text = "${DateTimeUtil.convert(subject.startTime)} - ${DateTimeUtil.convert(subject.endTime)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(cardColorScheme.timingContentColor).copy(alpha = contentAlpha),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Text(
                text = subject.subjectCode,
                fontSize = 13.sp,
                color = Color(cardColorScheme.instructorColor).copy(alpha = contentAlpha)
            )
            if (isCancelled) {
                Text(
                    text = "Already cancelled",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun MBSPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6366F1),
            contentColor = Color.White
        ),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MBSSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF6366F1)
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(Color(0xFF6366F1))
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
