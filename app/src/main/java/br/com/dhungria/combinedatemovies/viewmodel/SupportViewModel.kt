package br.com.dhungria.combinedatemovies.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.dhungria.combinedatemovies.data.models.SupportModel
import br.com.dhungria.combinedatemovies.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun uploadSupport(
        title: String,
        text: String,
        context: Context?,
        callback: ((Boolean) -> Unit)?
    ) {
        val uuid = UUID.randomUUID().toString()
        val saveSupport = SupportModel(
            id = uuid,
            title = title,
            text = text
        )
        repository.querySupportService().addOnCompleteListener {
            if (it.isSuccessful && !it.result.isEmpty) {
                if (title.isNotBlank() && text.isNotBlank()) {
                    repository.uploadSupport(saveSupport, uuid)
                    context?.let { context ->
                        Toast.makeText(
                            context,
                            "Sucesso, obrigado pelo feedback!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    callback?.invoke(true)
                } else {
                    context?.let {
                        Toast.makeText(
                            context,
                            "preencha todos os campos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                context?.let {
                    Toast.makeText(context, "Serviço indisponível no momento", Toast.LENGTH_LONG)
                        .show()
                }
                callback?.invoke(false)
            }

        }
    }

}