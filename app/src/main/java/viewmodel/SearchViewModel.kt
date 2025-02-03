package viewmodel

import androidx.lifecycle.*
import models.Gif
import repository.GiphyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GiphyRepository) : ViewModel() {

    // Поток для ввода поискового запроса
    private val _query = MutableStateFlow("")
    val query = _query

    // LiveData для списка GIF
    private val _gifs = MutableLiveData<List<Gif>>(emptyList())
    val gifs: LiveData<List<Gif>> = _gifs

    // LiveData для состояния загрузки и ошибок
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Параметры пагинации
    private var currentOffset = 0
    private var currentQuery = ""

    init {
        // Обработка автопоиска с debounce (задержка 500 мс)
        viewModelScope.launch {
            _query
                .debounce(500)
                .distinctUntilChanged()
                .collect { newQuery ->
                    if (newQuery.isNotBlank()) {
                        currentQuery = newQuery
                        currentOffset = 0
                        loadGifs(newQuery, currentOffset, reset = true)
                    } else {
                        // Если поле пустое, очищаем список
                        _gifs.postValue(emptyList())
                    }
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun loadNextPage() {
        if (currentQuery.isNotBlank() && isLoading.value == false) {
            loadGifs(currentQuery, currentOffset)
        }
    }

    private fun loadGifs(query: String, offset: Int, reset: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.searchGifs(query, offset)
                val newData = response.data
                currentOffset += newData.size
                if (reset) {
                    _gifs.value = newData
                } else {
                    val currentList = _gifs.value?.toMutableList() ?: mutableListOf()
                    currentList.addAll(newData)
                    _gifs.value = currentList
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки данных: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
