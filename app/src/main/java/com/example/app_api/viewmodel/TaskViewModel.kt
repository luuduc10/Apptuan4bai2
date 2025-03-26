package com.example.app_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_api.api.RetrofitInstance
import com.example.app_api.model.Task
import com.example.app_api.model.TaskResponse
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()  // ✅ Đúng tên biến
    val tasks: LiveData<List<Task>> = _tasks


    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTasks()
                if (response.isSuccessful) {
                    val taskResponse: TaskResponse? = response.body()
                    _tasks.value = taskResponse?.data ?: emptyList()
                } else {
                    println("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
            }
        }
    }

    fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        _tasks.value = _tasks.value?.map { task ->
            if (task.id == taskId) task.copy(isCompleted = isCompleted) else task
        }
    }
}
