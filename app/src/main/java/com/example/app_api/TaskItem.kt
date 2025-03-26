package com.example.app_api

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_api.model.Task //add this line
import androidx.compose.foundation.clickable


@Composable
fun TaskItem(task: Task, index: Int, onClick: () -> Unit) {
    val backgroundColor = when (index % 4) {
        0 -> Color(0xFFD0E8FF) // Xanh nhạt
        1 -> Color(0xFFE8C3C3) // Hồng nhạt
        2 -> Color(0xFFDDE4BB) // Xanh lá nhạt
        else -> Color(0xFFE8C3C3) // Hồng nhạt
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Đảm bảo TaskItem có thể click
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = task.title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = task.description,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Due Date: ${task.dueDate}",
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
