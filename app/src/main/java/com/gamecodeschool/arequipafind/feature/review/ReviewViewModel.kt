package com.gamecodeschool.arequipafind.feature.review
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamecodeschool.arequipafind.domain.models.Review
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.usecases.AddReviewUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
class ReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val authRepository: AuthRepository
):ViewModel(){
    private val _uiEvent= MutableSharedFlow<ReviewUiEvent>()
    val uiEvent=_uiEvent.asSharedFlow()

fun submitReview(jobId:String, reviewedId:String,rating:Int,comment:String){
    val reviewerId=authRepository.currentUserId()
    if(reviewerId==null){
        viewModelScope.launch { _uiEvent.emit(ReviewUiEvent.Error("no autentificado")) }
        return
    }
    val review=Review(
        id="",
        jobId=jobId,
        reviewerId=reviewerId,
        reviewedId = reviewedId,
        rating=rating,
        comment=comment
    )

    viewModelScope.launch{
        try{
            addReviewUseCase(jobId,reviewerId, reviewedId, rating, comment)
            _uiEvent.emit(ReviewUiEvent.Saved)
        }catch (e:Exception){
            _uiEvent.emit(ReviewUiEvent.Error(e.message ?: "Error al guardar reseña"))
        }

    }
}






sealed class ReviewUiEvent{
    object Saved : ReviewUiEvent()
    data class Error(val message :String):ReviewUiEvent()


}
}