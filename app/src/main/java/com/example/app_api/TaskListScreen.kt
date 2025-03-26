package com.example.app_api

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_api.model.Task
import com.example.app_api.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(taskViewModel: TaskViewModel = viewModel()) {
    val tasks by taskViewModel.tasks.observeAsState(initial = emptyList())
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    if (selectedTask != null) {
        TaskDetailScreen(task = selectedTask!!) {
            selectedTask = null
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.uth_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "SmartTasks",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }


            if (tasks.isEmpty()) {
                EmptyTaskListScreen()
            } else {
                LazyColumn {
                    itemsIndexed(tasks) { index, task ->
                        TaskItem(task = task, taskViewModel = taskViewModel) {
                            selectedTask = task
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun TaskItem(task: Task, taskViewModel: TaskViewModel, onClick: () -> Unit) {
    var isChecked by remember { mutableStateOf(task.isCompleted) } // Lưu trạng thái tích

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = getTaskColor(task))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    taskViewModel.updateTaskStatus(task.id, checked) // ✅ Cập nhật trạng thái
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(task.description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}


// Hàm để lấy màu nền theo trạng thái Task
fun getTaskColor(task: Task): Color {
    return when {
        task.isCompleted -> Color.LightGray // ✅ Nếu hoàn thành, đổi màu xám
        task.title.contains("Project") -> Color(0xFFB3E5FC)
        task.title.contains("Doctor") -> Color(0xFFFFCDD2)
        task.title.contains("Exam") -> Color(0xFFC8E6C9)
        else -> Color.White
    }
}




@Composable
fun TaskDetailScreen(task: Task, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) { // Gọi onBack() khi bấm
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                "Detail", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
        }

        Text(task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
        Text(task.description, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))

        if (task.subtasks.isNotEmpty()) {
            Text("Subtasks:", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
            task.subtasks.forEach { subtask ->
                var subtaskChecked by remember { mutableStateOf(false) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = subtaskChecked,
                        onCheckedChange = { subtaskChecked = it }
                    )
                    Text(subtask.title, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}


@Composable
fun EmptyTaskListScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_empty), // Đảm bảo bạn có icon này
            contentDescription = "No Tasks",
            modifier = Modifier.size(80.dp),
            tint = Color.Gray
        )
        Text("No Tasks Yet!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Stay productive—add something to do", fontSize = 14.sp, color = Color.Gray)
    }
}

